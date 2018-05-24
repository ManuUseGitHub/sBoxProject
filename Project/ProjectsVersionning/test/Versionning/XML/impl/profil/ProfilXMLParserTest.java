/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.XML.impl.profil;

import Versionning.XML.XMLExporter;
import Versionning.dataource.Profil;
import Versionning.dataource.Project;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MAZE2
 */
public class ProfilXMLParserTest {
    
    public ProfilXMLParserTest() {
    }

    @Test
    public void testSomeMethod() {
        try {
            File file = new File("Exported");
            file.mkdir();
            FileWriter writer = new FileWriter(file.getName()+"/"+"jean.xml");
            XMLExporter exporter = new ProfilsExporter();
            
            List<Project> projects = new ArrayList<>();
            
            projects.add(new Project(0, "projo"));
            projects.add(new Project(1, "projix"));
            projects.add(new Project(150, "pokemon"));
            
            Profil jean = new Profil();
            jean.setName("jean");
            jean.setProjects(projects);
            
            exporter.exporter(jean,writer);
            
        } catch (Exception ex) {
            Logger.getLogger(ProfilXMLParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
