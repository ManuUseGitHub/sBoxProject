/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.dataource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MAZE2
 */
public class Profil implements IProfil {

    private String name;
    private List<Project> projects;

    public Profil() {
        projects = new ArrayList<>();
    }

    //<editor-fold defaultstate="collapsed" desc="mutators and accessors">
    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the projects
     */
    @Override
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    @Override
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public void addProject(Project project) {
        projects.add(project);
    }
//</editor-fold>

    @Override
    public void deleteProject(Project project) {
        Project found = null;
        for (Project p : projects) {
            if (p.equals(project)) {
                found = p;
                break;
            }
        }
        if (found != null) {
            projects.remove(found);
        }
    }

    @Override
    public Project getProject(String name, int version) {
        String v = "" + version;
        for (Project p : projects) {
            if (p.getName().equals(name) && p.getVersion() == version) {
                return p;
            }
        }
        return null;
    }
}
