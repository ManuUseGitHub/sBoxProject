/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.XML.impl.project;

import Versionning.dataource.ProjectsList;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MAZE2
 */
public class projectsLoaderTest {
    
    public projectsLoaderTest() {
    }

    @Test
    public void testSomeMethod() {
        projectsLoader importeur = new projectsLoader();
        importeur.setFilePath(new File("Exported/projects.xml").toPath());
        ProjectsList projects = importeur.load();

        assertEquals(projects.getProjectsLines().size(), 3);
    }
    
}
