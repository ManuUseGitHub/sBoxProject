/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.XML.impl.project;

import Versionning.ConverterToXML;
import Versionning.dataource.Project;
import Versionning.dataource.ProjectsList;
import java.io.Serializable;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author MAZE2
 */
public class ProjectsXMLConverter extends ConverterToXML {

    @Override
    protected <T extends Serializable> void defineModel(T model, Document xmlDoc) {
        
        ProjectsList projects = (ProjectsList) model;

        XMLProjectLister lister = new XMLProjectLister();
        List<Project> projectsList = (List<Project>) projects.getProjectsLines().values();
        Element projectsNode = lister.getProjectsNode(projectsList, xmlDoc);
        
        xmlDoc.appendChild(projectsNode);
    }

}
