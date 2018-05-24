/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.commit;

import sboxclient.SubController;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.control.TextField;
import sboxclient.nextOkActionable.NextOkOrBadDisconnector;
import sboxclient.commit.CommitManager;
import sboxclient.FeedBackMessenger;
import sboxclient.nextOkActionable.NextOkQueryable;
import sboxclient.query.SBoxConnQueryController;

/**
 *
 * @author MAZE2
 */
public class CommitController extends SubController {

    private final Observer Advancement;
    private final FeedBackMessenger messenger;
    private TextField sourceFolder;
    private TextField id;

    public CommitController(FeedBackMessenger messenger, Observer Advancement, SBoxConnQueryController queryCtrl) {
        super(queryCtrl);
        this.Advancement = Advancement;
        this.messenger = messenger;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("COMMIT")) {
            CommitManager commitManager = new CommitManager(getClient(), Advancement);
            CommitTask task = new CommitTask(sourceFolder.getText(), id.getText());
            commit(commitManager, task);
        }
    }

    private void commit(CommitManager commitManager, CommitTask task) {
        File file;
        if ((file = new File(sourceFolder.getText())).exists()) {
            commitManager.setFile(file);

            task.addObserver(commitManager);

            getClient().setTask(task);
            getClient().addObserverOnTask(messenger);
            getClient().addObserverOnTask(new NextOkOrBadDisconnector(getClient()));
            getClient().addObserverOnTask(new NextOkQueryable(getClient(), getQueryer()));

            if (!id.getText().isEmpty()) {
                getClient().sendToServer("COMMIT " + id.getText());
            } else {
                task.setMessage("ERR C1 Veuillez entrer le nom du projet a COMMIT");
            }
        } else {
            task.setMessage("ERR C2 Veuillez saisir un dossier existant");
        }
    }

    public SubController setInputs(TextField txtFieldCommitId, TextField txtFieldCommitSourceFolder) {
        this.id = txtFieldCommitId;
        this.sourceFolder = txtFieldCommitSourceFolder;

        return this;
    }
}
