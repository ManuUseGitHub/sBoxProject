/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.sending;

import Versionning.XML.impl.project.projectsLoader;
import Versionning.dataource.ProjectsList;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import node.receiver.MultiCastSender;

/**
 *
 * @author MAZE2
 */
public abstract class ProjectSender {

    private final MultiCastSender sender;

    public ProjectSender(MultiCastSender sender) {
        this.sender = sender;
    }

    public abstract void sendProjects(String who, ProjectsList projects) throws UnknownHostException, IOException;

    public void send(String format) throws UnknownHostException, IOException {
        sender.send(format);
    }
    
    private String[] getAllProfilsFilenames(File repositoriesFolder) {
        //File[] folderContent = repositoriesFolder.listFiles();
        ArrayList<String> listOfFiles = new ArrayList<>();

        getXml(listOfFiles, repositoriesFolder);

        String[] result = new String[listOfFiles.size()];
        return listOfFiles.toArray(result);
    }

    private void getXml(ArrayList<String> files, File current) {
        if (current.isFile() && current.getName().matches(".*xml$")) {
            files.add(current.getName());
        } else if (current.isDirectory()) {
            for (File file : current.listFiles()) {
                getXml(files, file);
            }
        }
    }

    public void seekAndSendProjectRequest(String repositoryLocation) {
        String[] users = getAllProfilsFilenames(new File(repositoryLocation));

        for (String xmlfileName : users) {
            try {
                String who = xmlfileName.substring(0, xmlfileName.indexOf('.'));

                projectsLoader importeur = new projectsLoader();

                importeur.setFilePath(new File(String.format("%s/%s/%s", repositoryLocation, who, xmlfileName)).toPath());

                sendProjects(who, importeur.<ProjectsList>load());
            } catch (IOException ex) {
                Logger.getLogger(ProjectSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
