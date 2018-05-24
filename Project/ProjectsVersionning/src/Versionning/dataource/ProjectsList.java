/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.dataource;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MAZE2
 */
public class ProjectsList implements IProjectsList {

    private Map<String, Project> projectsLines;

    public ProjectsList() {
        projectsLines = new HashMap<>();
    }

    @Override
    public void addProject(Project project) {
        if (project.getVersion() >= 0) {
            projectsLines.put(project.toString(), project);
        }
    }

    /**
     * @return the projectsLines
     */
    @Override
    public Map<String, Project> getProjectsLines() {
        return projectsLines;
    }

    /**
     * @param projectsLines the projectsLines to set
     */
    @Override
    public void setProjectsLines(Map<String, Project> projectsLines) {
        this.projectsLines = projectsLines;
    }

}
