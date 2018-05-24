/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.SEEK;

import Versionning.dataource.Project;
import Versionning.dataource.ProjectsList;
import java.io.IOException;
import java.net.UnknownHostException;
import node.receiver.MultiCastSender;
import node.sending.ProjectSender;

/**
 *
 * @author MAZE2
 */
public class InfoSenderFromProjects extends ProjectSender{

    public InfoSenderFromProjects(MultiCastSender sender) {
        super(sender);
    }

    @Override
    public void sendProjects(String who, ProjectsList projects) throws UnknownHostException, IOException {
        for (Project project : projects.getProjectsLines().values()) {
            send(String.format("INFO %s %s %s", who, project.getName(), project.getVersion()));
        }
    }
}
