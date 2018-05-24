package Versionning.XML.impl.project;

import Versionning.ILoader;
import Versionning.XML.XMLFileReader;
import Versionning.dataource.Project;
import Versionning.dataource.ProjectsList;
import java.io.FileReader;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class projectsLoader implements ILoader {
    private Path path;

    @Override
    public <T extends Serializable> T load() {
        return (T) importerProjects();
    }

    /**
     * essaye d'importer la liste des projets. Si on passe un type T qui n'est
     * pas de type Liste-de-projets, une erreur est lancée
     *
     * @param <T>
     * @return
     */
    private <T extends ProjectsList> T importerProjects() {
        try {

            XMLFileReader lecteur = new XMLFileReader();
            NodeList liste = lecteur.readXMLRootNode("projects", new FileReader(path.toFile()));

            return (T) extractProjects(liste);
        } catch (Exception ex) {
            Logger.getLogger(projectsLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private ProjectsList extractProjects(NodeList list) {
        ProjectsList projects = new ProjectsList();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            Element element = (Element) node;

            // Il faut rechercher dans le noeud XML courrant pour retrouver les projets contenu dans le noeud projects 
            if (element.getNodeName().equals("projects")) {
                NodeList childs = list.item(i).getChildNodes();
                getChildrenOfProjectsNode(childs, projects);
            }
        }

        return projects;
    }

    private void getChildrenOfProjectsNode(NodeList childs, ProjectsList projects) {
        for (int i = 0; i < childs.getLength(); i++) {
            Node node = childs.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (node.getNodeName().equals("project")) {
                    Project project = new Project();
                    project.setVersion(Integer.parseInt(element.getAttribute("version")));
                    project.setName(element.getElementsByTagName("name").item(0).getTextContent());

                    projects.addProject(project);
                }
            }

        }
    }

    @Override
    public void setFilePath(Path path) {
        this.path = path;
    }
}
