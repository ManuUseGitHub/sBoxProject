/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.dataource;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author MAZE2
 */
public interface IProjectsList extends Serializable {

    void addProject(Project project);

    /**
     * @return the projectsLines
     */
    Map<String, Project> getProjectsLines();

    /**
     * @param projectsLines the projectsLines to set
     */
    void setProjectsLines(Map<String, Project> projectsLines);
    
}
