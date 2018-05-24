/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.pull;

import sboxclient.SubController;
import java.io.File;
import java.nio.file.Files;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.control.TextInputControl;
import sboxclient.nextOkActionable.NextOkOrBadDisconnector;
import sboxclient.pull.PullManager;
import sboxclient.MessageTaskParser;
import sboxclient.FeedBackMessenger;

/**
 *
 * @author MAZE2
 */
public class PullController extends SubController {

    private TextInputControl id;
    private TextInputControl destination;

    private final FeedBackMessenger messenger;
    private final Observer advancement;

    public PullController(FeedBackMessenger messenger, Observer advancement) {
        this.messenger = messenger;
        this.advancement = advancement;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("PULL")) {
            PullManager pullManager = new PullManager(advancement);

            PullTask task = new PullTask();
            task.setValues(id.getText(), destination.getText());
            getClient().setTask(task);

            task.addObserver(pullManager);
            task.addObserver(messenger);
            task.addObserver(new NextOkOrBadDisconnector(getClient()));

            if (isDestinationValid(task)) {
                getClient().sendToServer("PULL " + id.getText());
            }
        }

    }

    public PullController setInputs(TextInputControl id, TextInputControl destination) {
        this.destination = destination;
        this.id = id;
        return this;
    }

    private boolean isDestinationValid(PullTask task) {
        boolean destinationExist = new File(destination.getText()).exists();
        if (!destinationExist) {
            task.setMessage("ERR C2 destination invalide");
        } else {
            task.setMessage("OK C2 ");
        }
        return destinationExist;
    }
}
