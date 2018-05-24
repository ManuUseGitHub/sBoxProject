package node.receiving.RETRIEVE.for_storage;

import java.util.Observable;
import node.notImplementation.base.ClientController;
import node.TaskParser;
import node.notImplementation.InteractNetwork;

/**
 *
 * @author MAZE2
 */
public class RetrieveTaskController extends ClientController {

    private final RetrieveManager commitManager;

    public RetrieveTaskController(InteractNetwork client,String[] retrieveTaskParsed) {
        super(client,new RetrievetTaskAnalyser());
        commitManager = new RetrieveManager();
        commitManager.setWho(retrieveTaskParsed[0]);
        commitManager.initialize(retrieveTaskParsed);
    }

    @Override
    public void update(Observable obs, Object o) {
        super.update(obs, o);

        String message = getMessage();
        String analysed = getAnalyseResult();
        handleFluxListening(message, analysed);
    }

    private void handleFluxListening(String message, String analysed) {
        if (analysed.equals("ZIP")) {
            if (message.equals("###")){
                commitManager.close(true);
            } else {
                commitManager.writeZipLine(message);
            }
        }
        else if (analysed.equals("TAILLE")){
            int maximum = Integer.parseInt(new TaskParser().parseOneGroup(getMessage(), RetrievetTaskAnalyser.OK_WITH_SIZE));
            commitManager.setMaximumSteps(maximum);
        }
        else if (analysed.equals("OK")){
            sendResponse("QUIT");
      }
    }
}
