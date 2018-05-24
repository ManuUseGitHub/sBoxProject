/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.INFO;

import Versionning.XML.impl.project.projectsLoader;
import Versionning.dataource.Project;
import Versionning.dataource.ProjectsList;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import node.MultiCastSender;

/**
 *
 * @author MAZE2
 */
public class InfoSenderFromProjects {

    private final MultiCastSender sender;
    
    public InfoSenderFromProjects(MultiCastSender sender){
        this.sender = sender;
    }
            
    private void sendProjectForWhose(String who, ProjectsList projects) throws UnknownHostException, IOException {
        for (Project project : projects.getProjectsLines().values()) {
            sender.send(String.format("INFO %s %s %s", who, project.getName(), project.getVersion()));
        }
    }
    
    private String[] getAllProfilsFilenames(File repositoriesFolder) {
        File[] folderContent = repositoriesFolder.listFiles();
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
    public void seekAndSendInfo(String repositoryLocation){
        String[] users = getAllProfilsFilenames(new File(repositoryLocation));

            for (String xmlfileName : users) {
                String who = xmlfileName.substring(0, xmlfileName.indexOf('.'));
                try {
                    projectsLoader importeur = new projectsLoader();

                    importeur.setFilePath(new File(String.format("%s/%s/%s", repositoryLocation, who, xmlfileName)).toPath());

                    sendProjectForWhose(who, importeur.<ProjectsList>load());

                } catch (IOException ex) {
                    Logger.getLogger(ThreadInfoTaskController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
}
