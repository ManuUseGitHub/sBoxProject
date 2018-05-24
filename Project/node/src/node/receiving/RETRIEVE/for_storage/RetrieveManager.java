/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.RETRIEVE.for_storage;

import Stepping.implementations.VerboseStepper;
import node.receiving.RETRIEVE.ZipFluxOut;
import Versionning.ProjectManager;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import node.Constantes;

/**
 *
 * @author MAZE2
 */
public class RetrieveManager {

    private String who;
    private ProjectManager projectManager;
    private ZipFluxOut zipFluxOut;
    private VerboseStepper stepper;
    private int actualstep = 0;

    public void initialize(String[] parsed) {

        String projectName = parsed[1];
        int version = Integer.parseInt(parsed[2]);

        String fileName = String.format("%s/%s/%s %d.zip", Constantes.REPOSITORY_LOCATION, who, projectName, version);

        stepper = new VerboseStepper();
        projectManager = new ProjectManager(who, String.format("%s/%s", Constantes.REPOSITORY_LOCATION, who));
        projectManager.setProjectInfo(projectName, version);

        projectManager.putIncommingVersion();
        zipFluxOut = new ZipFluxOut();
        zipFluxOut.setup(new File(fileName));

    }

    public void writeZipLine(String message) {
        try {
            zipFluxOut.recevoirFichierCoteServeur(message);

            //<editor-fold defaultstate="collapsed" desc="stepping process">
            if (stepper.getAdvancement() < 1.0) {
                stepper.steppingWhileProcessing(++actualstep, String.format("transfer du fichier '%s'", zipFluxOut.getFile().getName()));
            }
            else{
                stepper.steppingDirectly(actualstep, String.format("transfer du fichier '%s' terminé", zipFluxOut.getFile().getName()));
            }
//</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(RetrieveTaskController.class.getName()).log(Level.SEVERE, null, ex);
            zipFluxOut.close();
        }
    }

    /**
     * @param who the who to set
     */
    public void setWho(String who) {
        this.who = who;
    }

    public void close(boolean success) {
        if (success) {
            projectManager.validate();
        }
        zipFluxOut.close();
    }

    public void setMaximumSteps(int maximum) {
        stepper.setMaximum(maximum);
    }
}
