/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning;

import Versionning.ProjectManager;
import Versionning.argument.ProjectDesciptor;
import Versionning.dataource.Profil;
import Versionning.dataource.Project;
import java.util.Observable;

/**
 *
 * @author MAZE2
 */
public class UnexistingProjectManager extends Observable {
    
    private final String repositoryLocation;

    public UnexistingProjectManager(String repositoryLocation) {
        this.repositoryLocation = repositoryLocation;
    }
    
    public void manageNotExistentPoject(String wWho,String wProjectName,int wVersion) {

        // on va manager les projets de <User>
        ProjectManager projectManager = new ProjectManager(wWho, String.format("%s/%s", repositoryLocation, wWho));
        Profil profil = projectManager.getProfil(); // profil chargé

        // on va récupérer le projet du fichier xml si il correspond à une entrée dans le fichier xml
        projectManager.setProjectInfo(wProjectName, wVersion);
        Project project = profil.getProject(wProjectName, wVersion);

        // dans le cas du fichier inexistant, on envoit WHOHAS
        if (project == null) {
            setChanged();
            notifyObservers(new ProjectDesciptor(projectManager, profil));
        }
    }
}
