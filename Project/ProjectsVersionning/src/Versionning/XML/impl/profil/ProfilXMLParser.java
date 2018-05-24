/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.XML.impl.profil;

import Versionning.ConverterToXML;
import Versionning.XML.impl.project.XMLProjectLister;
import Versionning.dataource.Profil;
import Versionning.dataource.Project;
import java.io.Serializable;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author MAZE2
 */
public class ProfilXMLParser extends ConverterToXML {

    @Override
    protected <T extends Serializable> void defineModel(T model, Document xmlDoc) {

        // spécification du profil
        Profil profil = (Profil) model;

        Element profilNode = xmlDoc.createElement("profil");

        Element profilName = xmlDoc.createElement("profilName");
        profilName.appendChild(xmlDoc.createTextNode(profil.getName()));

        profilNode.appendChild(profilName);

        // spécification de la liste des projets pour le profil
        XMLProjectLister lister = new XMLProjectLister();
        
        List<Project> projectsList = profil.getProjects();
        Element projectsNode = lister.getProjectsNode(projectsList, xmlDoc);

        profilNode.appendChild(projectsNode);
        
        xmlDoc.appendChild(profilNode);
    }

}
