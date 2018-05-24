package node.sending.RETRIEVE;

import util.Parser;
import Server.notimpl.InteractNetwork;
import Server.notimpl.base.ClientController;
import Stepping.Stepper;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import node.MultiCastSender;

/**
 *
 * @author MAZE2
 */
public class RetrieveForTransferTaskController extends ClientController {

    private final RetrieveManager retrieveManager;
    private final MultiCastSender sender;

    public RetrieveForTransferTaskController(MultiCastSender sender, InteractNetwork client, String[] retrieveTaskParsed, Stepper otherStepper) {
        super(client, new RetrievetTaskAnalyser());
        retrieveManager = new RetrieveManager(retrieveTaskParsed);
        retrieveManager.initialize(retrieveTaskParsed, otherStepper);
        
        this.sender = sender;
    }

    @Override
    public void update(Observable obs, Object o) {
        super.update(obs, o);

        String message = getMessage();
        String analysed = getAnalyseResult();

        if (analysed.equals("ZIP")) {
            if (message.equals("###")) {
                retrieveManager.close(true);
                terminateProcess();
            } else {
                retrieveManager.writeZipLine(message);
            }
        } else if (analysed.equals("TAILLE")) {
            int maximum = Integer.parseInt(new Parser().parseOneGroup(getMessage(), RetrievetTaskAnalyser.OK_WITH_SIZE));
            retrieveManager.setMaximumSteps(maximum);
        } else if (analysed.equals("OK")) {
            sendResponse("QUIT");
        }
    }

    private void terminateProcess() {
        try {
            sender.send("OK Pulled");
        } catch (IOException ex) {
            Logger.getLogger(RetrieveForTransferTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
