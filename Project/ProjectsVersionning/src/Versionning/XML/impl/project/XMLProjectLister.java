/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.XML.impl.project;

import Versionning.dataource.Project;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author MAZE2
 */
public class XMLProjectLister {
    public Element getProjectsNode(List<Project> list, Document xmlDoc){
        Element rootNode = xmlDoc.createElement("projects");
        for (Project project : list) {
            // creating nodes
            Element projectNode = xmlDoc.createElement("project");

            // Creating text nodes
            Element nameNode = xmlDoc.createElement("name");
            Text name = xmlDoc.createTextNode(project.getName());

            nameNode.appendChild(name);

            // settingAttributes
            projectNode.setAttribute("version", project.getVersion() + "");
            projectNode.appendChild(nameNode);
            rootNode.appendChild(projectNode);
        }
        return rootNode;
    }
}
