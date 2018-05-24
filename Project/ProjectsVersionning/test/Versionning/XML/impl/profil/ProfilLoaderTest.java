/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.XML.impl.profil;

import Versionning.dataource.Profil;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MAZE2
 */
public class ProfilLoaderTest {
    
    public ProfilLoaderTest() {
    }

    @Test
    public void testSomeMethod() {
        ProfilLoader loader = new ProfilLoader();
        
        loader.setFilePath(new File("exported/jean.xml").toPath());

        Profil profil = loader.<Profil>load();

        assertEquals(profil.getProjects().size(), 3);
        assertEquals(profil.getName(), "jean");

    }
    
}
