/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.pull;

import java.util.Observable;
import javafx.scene.control.Label;
import sboxclient.TaskAnalyser;
import sboxclient.FeedBackMessenger;

/**
 *
 * @author MAZE2
 */
public class PullMessenger extends FeedBackMessenger {

    private final TaskAnalyser analyser;
    private final Label lblProcess;
    private final Label lblDestination;
    private final Label lblId;

    public PullMessenger(Label lblId, Label lblProcess,Label lblDestination) {
        this.lblId = lblId;
        this.lblProcess = lblProcess;
        this.lblDestination = lblDestination;
        this.analyser = new TaskAnalyser();
    }
    
    private void handleWithCode(String analyseResult, PullTask task) {    
        String[] args = getParsedWithCodeOrDefault(task.getMessage());
        
        String code = args[CODE_INDEX];
        String foundMessage = args[MESSAGE_INDEX];

        switch (Integer.parseInt(code)) {
            case 1:
                setBadOrGoodFeedback(lblId,analyseResult, foundMessage);
                break;
            case 2:
                setBadOrGoodFeedback(lblDestination,analyseResult, foundMessage);
                break;
            case 3:
                setBadOrGoodFeedback(lblProcess,analyseResult, foundMessage);
                break;
        }
    }

    @Override
    public void update(Observable o, Object o1) {

        if (o instanceof PullTask) {
            String analysed = analyser.analyse(((PullTask) o).getMessage());
            handleWithCode(analysed, (PullTask) o);
        }
    }
}
