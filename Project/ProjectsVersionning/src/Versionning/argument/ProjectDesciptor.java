/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.argument;

import Versionning.ProjectManager;
import Versionning.dataource.Profil;

/**
 *
 * @author MAZE2
 */
public class ProjectDesciptor {
    private final ProjectManager manager;
    private final Profil profil;

    public ProjectDesciptor(ProjectManager manager, Profil profil) {
        this.manager = manager;
        this.profil = profil;
    }

    /**
     * @return the manager
     */
    public ProjectManager getManager() {
        return manager;
    }

    /**
     * @return the profil
     */
    public Profil getProfil() {
        return profil;
    }
    
}
