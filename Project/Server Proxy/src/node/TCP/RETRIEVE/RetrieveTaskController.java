package node.TCP.RETRIEVE;

import Server.Analysis;
import Server.Constantes;
import Server.notimpl.base.Server;
import util.Parser;
import node.MultiCastSender;
import Server.notimpl.base.MCController;
import Server.pull.ZipTransferer;
import Stepping.implementations.VerboseStepper;
import java.io.File;
import java.util.Observable;

/**
 *
 * @author MAZE2
 */
public class RetrieveTaskController extends MCController {

    private final Parser parser;
    
    public RetrieveTaskController(MultiCastSender sender) {
        super(new RetrieveTaskAnalyser(), sender);
        parser = new Parser();
    }

    @Override
    public void update(Observable obs, Object arg) {
        super.update(obs, arg);

        String message = getMessage();
        String analysed = getAnalyseResult();
        switch (analysed) {
            case "RECUPERER":
                onRetrieveEvent(obs, message);
                break;
        }
    }

    private void onRetrieveEvent(final Observable obs, final String message) {

        new Thread(() -> {

            VerboseStepper stepper = new VerboseStepper();

            String[] result = parser.parseMultipleGroups(message, RetrieveTaskAnalyser.RETRIEVE);

            File file = new File(String.format("%s/%s/%s %s.zip", Constantes.REPOSITORIES_LOCATION, result[0], result[1], result[2]));

            //<editor-fold defaultstate="collapsed" desc="stepping: setting the maximum">
            int maximum = (int) Analysis.size(file.toPath()) / 57 - 1;
            stepper.setMaximum(maximum);
            ((Server) obs).sendResponse("OK " + maximum);
//</editor-fold>

            ZipTransferer.sendFileTo(file, result[0], (Server) obs, stepper);
            ((Server) obs).sendResponse("OK fin transfer");
            file.delete();
        }).start();
    }
}
