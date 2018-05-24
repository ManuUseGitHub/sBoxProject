/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.XML.impl.profil;

import Versionning.ILoader;
import Versionning.XML.XMLFileReader;
import Versionning.XML.impl.project.projectsLoader;
import Versionning.dataource.Profil;
import Versionning.dataource.Project;
import Versionning.dataource.ProjectsList;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author MAZE2
 */
public class ProfilLoader implements ILoader {
    private Path path;

    @Override
    public <T extends Serializable> T load() {
        return (T) loadProfile();
    }
    
    public static Profil load(File file){
        ProfilLoader loader = new ProfilLoader();
        loader.setFilePath(file.toPath());
        return loader.load();
    }
    private <T extends Profil> T loadProfile() {
        try {
            Profil profil = new Profil();

            XMLFileReader lecteur = new XMLFileReader();
            NodeList liste = lecteur.readXMLRootNode("profil", new FileReader(path.toFile()));
            
            // on récupère le(s) noeuds de type "profil" puis le transforme en élément pour pouvoir faire des recherches
            Node profilNode = liste.item(0);
            Element elmentNode = (Element) profilNode;

            // on va ajouter le nom au profil
            String name = elmentNode.getElementsByTagName("profilName").item(0).getTextContent();
            profil.setName(name);
            
            // on va extraire les projets du profil
            List<Project> projects = extractProjects();
            profil.setProjects(projects);
            return (T) profil;
        } catch (Exception ex) {
            Logger.getLogger(ProfilLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Project> extractProjects() {
        projectsLoader loader = new projectsLoader();
        loader.setFilePath(path);
        Map<String, Project> lines = loader.<ProjectsList>load().getProjectsLines();
        List<Project> projects = new ArrayList<>(lines.values());

        return projects;
    }

    @Override
    public void setFilePath(Path path) {
        this.path = path;
    }

}
