/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.notimpl.base;

import Server.notimpl.IProject;

/**
 *
 * @author MAZE2
 */
public abstract class ProjectModel implements IProject {
    
    private final String projectName;
    private final String ProjectVersion;

    public ProjectModel(String projectName, String ProjectVersion) {
        this.projectName = projectName;
        this.ProjectVersion = ProjectVersion;
    }

    /**
     * @return the projectName
     */
    @Override
    public String getProjectName() {
        return projectName;
    }

    /**
     * @return the ProjectVersion
     */
    @Override
    public String getProjectVersion() {
        return ProjectVersion;
    }
}
