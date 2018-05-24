/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.sending.RESTORE;

import node.sending.ProjectSender;
import Versionning.dataource.Project;
import Versionning.dataource.ProjectsList;
import java.io.IOException;
import java.net.UnknownHostException;
import node.receiver.MultiCastSender;

/**
 *
 * @author MAZE2
 */
public class RestoreSenderFromProjects extends ProjectSender{
    
    public RestoreSenderFromProjects(MultiCastSender sender) {
        super(sender);
    }

    @Override
    public void sendProjects(String who, ProjectsList projects) throws UnknownHostException, IOException {
        for (Project project : projects.getProjectsLines().values()) {
            super.send(String.format("RESTORE %s %s %s", who, project.getName(), project.getVersion()));
        }
    }
}
