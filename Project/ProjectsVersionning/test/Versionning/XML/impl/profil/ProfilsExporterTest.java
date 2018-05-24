/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.XML.impl.profil;

import Versionning.XML.XMLExporter;
import Versionning.XML.impl.project.ProjectsXMLConverter;
import Versionning.dataource.Profil;
import Versionning.dataource.Project;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MAZE2
 */
public class ProfilsExporterTest {
    
    public ProfilsExporterTest() {
    }

    @Test
    public void exportingSucceed() {

        StringWriter writer = new StringWriter();
        XMLExporter exporter = new XMLExporter(new ProjectsXMLConverter()) ;
        
        exporter.exporter(getSampleProject(),writer);

        System.out.print(writer.toString());
    }

    @Test
    public void exportingAFileSucceed() {
        try {
            File file = new File("Exported");
            file.mkdir();
            FileWriter writer = new FileWriter(file.getName()+"/"+"project.xml");
            XMLExporter exporter = new XMLExporter(new ProjectsXMLConverter());
            
            exporter.exporter(getSampleProject(),writer);
            
        } catch (Exception ex) {
            Logger.getLogger(ProfilsExporterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Project getSampleProject() {
        ArrayList<Profil> profiles = new ArrayList<>();

        Project project = new Project();
        project.setName("projo");
        project.setVersion(0);

        return project;
    }
    
}
