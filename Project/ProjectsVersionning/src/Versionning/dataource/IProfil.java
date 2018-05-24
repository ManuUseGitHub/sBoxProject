/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.dataource;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author MAZE2
 */
public interface IProfil extends Serializable {

    void addProject(Project project);
    //</editor-fold>

    void deleteProject(Project project);

    //<editor-fold defaultstate="collapsed" desc="mutators and accessors">
    /**
     * @return the name
     */
    String getName();

    Project getProject(String name, int version);

    /**
     * @return the projects
     */
    List<Project> getProjects();

    /**
     * @param name the name to set
     */
    void setName(String name);

    /**
     * @param projects the projects to set
     */
    void setProjects(List<Project> projects);
    
}
