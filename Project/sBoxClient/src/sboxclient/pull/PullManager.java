/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.pull;

import Stepping.implementations.SoftStepper;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import sboxclient.MessageTaskParser;
import sboxclient.commit.ZipFluxOut;

/**
 *
 * @author MAZE2
 */
public class PullManager implements Observer {

    private final SoftStepper stepper;
    private final PullTaskAnalyser analyser;
    private boolean pulling;
    private final ZipFluxOut zipFlux;
    private int step = 0;

    public PullManager(Observer obs) {
        stepper = SoftStepper.newStepper(obs);
        analyser = new PullTaskAnalyser();
        zipFlux = new ZipFluxOut();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof PullTask) {
            String message = ((PullTask) o).getMessage();

            String[] parsed;
            String maximum;

            // OK C4 bla bla bla ...
            if (!isPulling()) {
                if (analyser.analyse(message).equals("GOOD_WITH_CODE_INSIDE") && (parsed = MessageTaskParser.parseOK_WITH_CODE(message))[0].equals("4")) {
                    stepper.setQueuedAdvMessage(parsed[1]);   // message à destination de la barre de progression
                } else if (analyser.analyse(message).equals("GOOD") && (maximum = MessageTaskParser.parseOK(message)).matches("^[0-9]*$")) {
                    pulling = true;
                    stepper.setMaximum(Integer.parseInt(maximum));
                    zipFlux.setup(new File(((PullTask) o).getDestination() + "/" + ((PullTask) o).getId() + ".zip"));
                    ((PullTask)o).setNextOkCloseable(true);
                }
                else if(analyser.analyse(message).equals("ERR_WITH_CODE_INSIDE")){
                    zipFlux.close();
                }
            } else {
                pulling(message);
            }
        }
    }

    private void pulling(String message) {
        if (analyser.analyse(message).equals("ZIP")) {

            if (message.equals("###")) {
                pulling = false;
                zipFlux.close();
                Unzipper.unzip(zipFlux.getEndFile().getPath());
                stepper.terminer();
            } else {
                try {
                    stepper.updateAdvancement(++step);
                    stepper.setQueuedAdvMessage(message);
                    zipFlux.recevoirFichierCoteServeur(message);
                } catch (IOException ex) {
                    zipFlux.close();
                }
            }
        }
    }

    /**
     * @return the pulling
     */
    public boolean isPulling() {
        return pulling;
    }
}
