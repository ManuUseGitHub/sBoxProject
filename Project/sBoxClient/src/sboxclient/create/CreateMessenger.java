/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.create;

import java.util.Observable;
import javafx.scene.control.Label;
import sboxclient.TaskAnalyser;
import sboxclient.MessageTaskParser;
import sboxclient.FeedBackMessenger;

/**
 *
 * @author MAZE2
 */
public class CreateMessenger extends FeedBackMessenger {

    private final TaskAnalyser analyser;
    private final Label lblProjectId;
    private final Label lblProjectProcess;

    public CreateMessenger(Label lblProjectId, Label lblProjectProcess) {
        this.analyser = new TaskAnalyser();
        this.lblProjectId = lblProjectId;
        this.lblProjectProcess = lblProjectProcess;
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof CreateTask) {
            String message = ((CreateTask) o).getMessage();
            String analized = analyser.analyse(message);
            if (analized.equals("BAD")) {
                setEmptyFeedback(lblProjectProcess);
                setBadFeedback(lblProjectId, MessageTaskParser.parseERR(message));

            } else if (analized.equals("GOOD")) {
                if (!analized.equals("GOOD_QUIT")) {
                    setEmptyFeedback(lblProjectId);
                    setBadOrGoodFeedback(lblProjectProcess, analized, MessageTaskParser.parseOK(message));
                }
            }
        }
    }
}
