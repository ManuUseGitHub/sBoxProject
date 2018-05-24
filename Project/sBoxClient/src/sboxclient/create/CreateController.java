/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.create;

import sboxclient.nextOkActionable.NextOkOrBadDisconnector;
import sboxclient.SubController;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import sboxclient.FeedBackMessenger;
import sboxclient.nextOkActionable.NextOkQueryable;

/**
 *
 * @author MAZE2
 */
public class CreateController extends SubController {

    private TextInputControl projectName;

    private final FeedBackMessenger messenger;

    public CreateController(FeedBackMessenger messenger,Observer queryer) {
        super(queryer);
        this.messenger = messenger;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("CREATE")) {
            onCreateReceived();
        }
    }

    public SubController setInputs(TextField txtFieldProjectName) {
        this.projectName = txtFieldProjectName;
        return this;
    }

    private void onCreateReceived() {
        new Thread(() -> {
            sleep(2000);
            CreateTask task = new CreateTask();
            task.setNextOkCloseable(true);
            
            task.setValues(projectName.getText());
            
            getClient().setTask(task);
            getClient().addObserverOnTask(messenger);
            getClient().addObserverOnTask(new NextOkOrBadDisconnector(getClient()));
            getClient().addObserverOnTask(new NextOkQueryable(getClient(), getQueryer()));
            
            getClient().sendToServer("CREATE " + projectName.getText());
        }).start();
    }

}
