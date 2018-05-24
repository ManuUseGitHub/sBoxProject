/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.create.ObserverCreateTaskController;
import Versionning.XML.impl.profil.ProfilLoader;
import Versionning.XML.impl.profil.ProfilsExporter;
import Versionning.dataource.Profil;
import Versionning.dataource.Project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MAZE2
 */
public class UserFileManager {

    /**
     * Exporte un fichier user.xml dans un dossier user du dossier REPOSITORY avec le nouveau projet ou sans si project est null
     * @param who
     * @param project 
     */
    public static void createWhoseXMLWithProject(String who, Project project) {
        try {

            String folderPathName = String.format("%s/%s", Constantes.REPOSITORIES_LOCATION, who);
            new File(folderPathName).mkdir();                   // creation du dossier de l'utilisateur

            File file = new File(String.format("%s/%s.xml", folderPathName, who));
            Profil whoseProfil = getNewOrLoadProfil(who, file);

            if (project != null) {
                whoseProfil.addProject(project);
            }

            ProfilsExporter exporter = new ProfilsExporter();       // crée le fichier xml
            Writer writer = new FileWriter(file);
            exporter.exporter(whoseProfil, writer);                 // le fichier XML de l'utilisateur est écrit

        } catch (IOException ex) {
            Logger.getLogger(UserFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void createWhoseXML(String who) {
        createWhoseXMLWithProject(who, null);
    }

    /**
     * Load le profile si il existe , le crée sinon
     *
     * @param who
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    private static Profil getNewOrLoadProfil(String who, File file) throws FileNotFoundException {
        Profil whoseProfil;
        if (file.exists()) {
            whoseProfil = ProfilLoader.load(file);
        } else {
            whoseProfil = new Profil();
            whoseProfil.setName(who);
        }
        return whoseProfil;
    }

}
