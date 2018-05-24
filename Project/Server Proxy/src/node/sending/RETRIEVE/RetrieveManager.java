/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.sending.RETRIEVE;

import Server.Constantes;
import Server.ForWhoseProjectModel;
import Server.commit.ZipFluxOut;
import Server.notimpl.base.ProjectModel;
import Stepping.Stepper;
import Stepping.implementations.VerboseStepper;
import Versionning.ProjectManager;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MAZE2
 */
public class RetrieveManager {

    private ZipFluxOut zipFluxOut;
    private VerboseStepper stepper;
    private int actualstep = 0;
    private Stepper otherStepper;
    private final ForWhoseProjectModel project;

    RetrieveManager(String[] retrieveTaskParsed) {
        this.project = new ForWhoseProjectModel(retrieveTaskParsed);
    }

    public void initialize(String[] parsed, Stepper otherUseStepper) {

        String projectName = parsed[1];
        int version = Integer.parseInt(parsed[2]);

        String whosePullFolder = String.format("%s/%s", Constantes.REPOSITORIES_PULL_LOCATION, project.getWhose());
        new File(whosePullFolder).mkdir();

        String fileName = String.format("%s/%s %d.zip", whosePullFolder, projectName, version);

        otherStepper = otherUseStepper;
        stepper = new VerboseStepper();

        zipFluxOut = new ZipFluxOut();
        zipFluxOut.setup(new File(fileName));

    }

    public void writeZipLine(String message) {

        try {
            zipFluxOut.recevoirFichierCoteServeur(message);

            steppingProcess();
        } catch (IOException ex) {
            Logger.getLogger(RetrieveManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void steppingProcess() {
        String messageToStepper = String.format("transfer du fichier '%s'", zipFluxOut.getFile().getName());
        String messageToView = String.format("%d etapes faites %s=>%s", actualstep, messageToStepper.replace("'", "").replace(" ", ""), project.getWhose());

        if (stepper.getAdvancement() < 1.0) {
            otherStepper.setAdvancement(actualstep);
            otherStepper.setQueuedAdvMessageTimed(messageToView, 100);
            stepper.steppingWhileProcessing(++actualstep, messageToStepper);
        } else {
            otherStepper.setAdvancement(actualstep);
            otherStepper.setDirectAdvancementMessage(messageToView);
            stepper.steppingDirectly(actualstep, messageToStepper + " terminé");
        }
    }
    public void close(boolean success) {
        if (success) {
        }
        zipFluxOut.close();
    }

    public void setMaximumSteps(int maximum) {
        stepper.setMaximum(maximum);
    }
}
