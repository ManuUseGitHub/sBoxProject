/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning;

import Versionning.XML.impl.profil.ProfilLoader;
import Versionning.XML.impl.profil.ProfilsExporter;
import Versionning.dataource.Profil;
import Versionning.dataource.Project;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MAZE2
 */
public class ProjectManager {

    private final ProfilLoader profilLoader;
    private final Profil profil;
    private final File fileToProfil;
    private int wVersion;       //working version
    private String wProjectName;//working project name

    public ProjectManager(String who,String repositoryLocation) {

        this.profilLoader = new ProfilLoader();
        fileToProfil = new File(String.format("%s/%s.xml",repositoryLocation,who));
        profilLoader.setFilePath(fileToProfil.toPath());
        profil = profilLoader.load();

    }
    
    public Profil getProfil() {
        return profil;
    }

    /**
     * La nouvelle version est passée on inverse la version en positif
     */
    public void validate() {
        getProfil().deleteProject(new Project(getwVersion(), getwProjectName()));
        wVersion = -getwVersion();
        Project validatedProject = new Project(getwVersion(), getwProjectName());
        getProfil().addProject(validatedProject);

        overwhriteXML();
    }

    /**
     * Met à jour la version du projet et rend négative cette version
     * (potentiellement corrompue)
     *
     */
    public void putIncommingVersion() {
        wVersion = -wVersion;
        getProfil().addProject(new Project(getwVersion(), getwProjectName()));

        overwhriteXML();
    }
    
    /**
     * Met à jour la version du projet et rend négative cette version
     * (potentiellement corrompue)
     *
     */
    public void putIncommingUpperVersion() {
        wVersion = -(wVersion + 1);
        getProfil().addProject(new Project(getwVersion(), getwProjectName()));

        overwhriteXML();
    }

    private Project findProject(Profil profil, Project project) {
        for (Project p : profil.getProjects()) {
            if (project.equals(p)) {
                return p;
            }
        }
        return null;
    }

    public void overwhriteXML() {
        try {
            ProfilsExporter exporter = new ProfilsExporter();
            exporter.exporter(getProfil(), new FileWriter(fileToProfil));
        } catch (IOException ex) {
            Logger.getLogger(ProjectManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isNextVersionexist() {

        Project project = new Project(getwVersion() + 1, getwProjectName());
        return findProject(getProfil(), project) != null;

    }

    public void setProjectInfo(String projectName, int version) {
        this.wProjectName = projectName;
        this.wVersion = version;
    }

    /**
     * @return the working-on-version
     */
    public int getwVersion() {
        return wVersion;
    }

    /**
     * @return the working-on-project-name
     */
    public String getwProjectName() {
        return wProjectName;
    }
}
