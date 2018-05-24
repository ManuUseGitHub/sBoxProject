/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.INFO.ProjectsCheck.WHOHAS;

import Versionning.argument.ProjectDesciptor;
import Versionning.dataource.Project;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import node.environnement.SingletonWhohasList;
import node.receiver.MultiCastSender;
import node.receiving.INFO.InfoTaskController;

/**
 *
 * @author MAZE2
 */
public class WHOHASMessageSender implements Observer {

    private int wVersion;
    private String wProjectName;
    private String wWho;
    private final MultiCastSender sender;
    private final SingletonWhohasList providingList = SingletonWhohasList.getINSTANTCE();

    public WHOHASMessageSender(MultiCastSender sender) {
        this.sender = sender;
    }

    public void setWorkingOn(String[] parsed) {
        wWho = parsed[0];
        wProjectName = parsed[1];
        wVersion = Integer.parseInt(parsed[2]);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ProjectDesciptor) {
            ProjectDesciptor pjd = (ProjectDesciptor) arg;

            try {
                if (wVersion == 0) {
                    pjd.getProfil().addProject(new Project(wVersion, wProjectName));
                    pjd.getManager().overwhriteXML();

                } else {
                    String whohasTask = String.format("WHOHAS %s %s %d", pjd.getProfil().getName(), wProjectName, wVersion);
                    providingList.addWhohasTask(wWho, wProjectName, wVersion + "");
                    System.out.println(String.format("(to Other) << %s", whohasTask));
                    sender.send(whohasTask);
                }
            } catch (IOException ex) {
                Logger.getLogger(InfoTaskController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
