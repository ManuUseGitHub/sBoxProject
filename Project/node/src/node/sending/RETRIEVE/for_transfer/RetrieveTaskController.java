package node.sending.RETRIEVE.for_transfer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Stepping.implementations.VerboseStepper;
import java.io.File;
import java.util.Observable;
import node.Analysis;
import node.TaskParser;
import node.notImplementation.InteractNetwork;
import node.notImplementation.base.ObservableServer;
import node.notImplementation.base.ServerTCPController;
import node.Constantes;
import node.receiving.RETRIEVE.ZipTransferer;

/**
 *
 * @author MAZE2
 */
public class RetrieveTaskController extends ServerTCPController {

    private final TaskParser parser;

    public RetrieveTaskController(ObservableServer server) {
        super(server, new RetrieveTaskAnalyser());
        parser = new TaskParser();
    }

    @Override
    public void update(Observable obs, Object arg) {
        super.update(obs, arg);

        String message = getMessage();
        String analysed = getAnalyseResult();
        switch (analysed) {
            case "RECUPERER":
                onRetrieveEvent((ObservableServer) obs, message);
                break;
        }
    }

    private void onRetrieveEvent(final ObservableServer obs, final String message) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                VerboseStepper stepper = new VerboseStepper();

                String[] result = parser.parseMultipleGroups(message, RetrieveTaskAnalyser.RETRIEVE);

                File file = new File(String.format("%s/%s/%s %s.zip", Constantes.REPOSITORY_LOCATION, result[0], result[1], result[2]));
                
                //<editor-fold defaultstate="collapsed" desc="stepping: setting the maximum">
                int maximum = (int) Analysis.size(file.toPath()) / 57 - 1;
                stepper.setMaximum(maximum);
                ((InteractNetwork) obs).sendResponse("OK " + maximum);
//</editor-fold>
                
                ZipTransferer.sendFileTo(file, result[0], (ObservableServer) obs, stepper);
                ((ObservableServer) obs).sendResponse("OK fin transfer");
                //file.delete();
            }
        }).start();
    }
}
