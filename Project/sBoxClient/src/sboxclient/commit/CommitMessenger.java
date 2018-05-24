/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.commit;

import java.util.Observable;
import javafx.scene.control.Label;
import sboxclient.TaskAnalyser;
import sboxclient.MessageTaskParser;
import sboxclient.FeedBackMessenger;

/**
 *
 * @author MAZE2
 */
public class CommitMessenger extends FeedBackMessenger{

    private final TaskAnalyser analyser;
    private final Label lblId;
    private final Label lblSourceFolder;
    private final Label lblProcessFeedBack;

    public CommitMessenger(Label lblIdFeedBack, Label lblSourceFolderFeedBack,Label lblProcessFeedBack) {
        this.lblId = lblIdFeedBack;
        this.lblSourceFolder = lblSourceFolderFeedBack;
        this.lblProcessFeedBack = lblProcessFeedBack;
        this.analyser = new TaskAnalyser();
    }

    
    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof CommitTask) {
            String result;
            String message = ((CommitTask)o).getMessage();
            switch (result = analyser.analyse(((CommitTask) o).getMessage())) {
                case "BAD_WITH_CODE_INSIDE":
                    handleWithCode(result, (CommitTask) o);
                    break;
                case "BAD":
                    setBadOrGoodFeedback(lblId,result, MessageTaskParser.parseERR(message));
                    break;
                case "GOOD_WITH_CODE_INSIDE":
                    handleWithCode(result, (CommitTask) o);
                    break;
                case "GOOD":
                    setBadOrGoodFeedback(lblId,result, MessageTaskParser.parseOK(message));
                    break;
            }
        }
    }
    
    private void handleWithCode(String analyseResult, CommitTask task) {
        
        String[] args = getParsedWithCodeOrDefault(task.getMessage());
        
        String code = args[0];
        String foundMessage = args[1];

        switch (Integer.parseInt(code)) {
            case 1:
                setBadOrGoodFeedback(lblId,analyseResult, foundMessage);
                break;
            case 2:
                setBadOrGoodFeedback(lblSourceFolder,analyseResult, foundMessage);
                break;
            case 3:
                setBadOrGoodFeedback(lblProcessFeedBack,analyseResult, foundMessage);
                break;
        }
    }
}
