/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.XML;

import Versionning.XML.impl.project.ProjectsXMLConverter;
import Versionning.dataource.Project;
import Versionning.dataource.ProjectsList;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MAZE2
 */
public class XMLExporterTest {
    
    public XMLExporterTest() {
    }

    @Test
    public void testSomeMethod() {
        try {
            File file = new File("Exported");
            file.mkdir();
            FileWriter writer = new FileWriter(file.getName()+"/"+"projects.xml");
            XMLExporter exporter = new XMLExporter(new ProjectsXMLConverter());
            
            exporter.exporter(getSampleProjects(),writer);
            
        } catch (Exception ex) {
            Logger.getLogger(XMLExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private ProjectsList getSampleProjects() {
        ProjectsList projects = new ProjectsList();
        
        Project project = new Project();
        project.setName("projo");
        project.setVersion(0);
        
        projects.addProject(project);
        
        return projects;
    }
    
}
