/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.connexion;

import java.util.Observable;
import javafx.scene.control.Label;
import sboxclient.Task;
import sboxclient.TaskAnalyser;
import sboxclient.MessageTaskParser;
import sboxclient.FeedBackMessenger;

/**
 *
 * @author MAZE2
 */
public class ConnexionMessenger extends FeedBackMessenger {

    private final Label lblFeedBackConnection;
    private final TaskAnalyser analyser;

    public ConnexionMessenger(Label lblFeedBackConnexion) {
        this.analyser = new TaskAnalyser();
        this.lblFeedBackConnection = lblFeedBackConnexion;
    }

    @Override
    public void update(Observable o, Object o1) {
        String message = o instanceof Task ? ((Task) o).getMessage() : "";

        String analized = analyser.analyse(message);
        if (o instanceof ConnexionTask) {
            if (analized.equals("BAD")) {
                setBadFeedback(lblFeedBackConnection, MessageTaskParser.parseERR(message));
            } else if (analized.equals("GOOD")) {
                setBadOrGoodFeedback(lblFeedBackConnection,analized,MessageTaskParser.parseOK(message));
            }
        }
    }
}
