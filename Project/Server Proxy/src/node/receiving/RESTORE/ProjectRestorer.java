/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.RESTORE;

import Versionning.argument.ProjectDesciptor;
import Versionning.dataource.Project;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author MAZE2
 */
public class ProjectRestorer implements Observer{

    private String wWho;
    private String wProjectName;
    private int wVersion;

    public void setWorkingOn(String[] parsed) {
        wWho = parsed[0];
        wProjectName = parsed[1];
        wVersion = Integer.parseInt(parsed[2]);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof ProjectDesciptor){
            ProjectDesciptor pjd = (ProjectDesciptor)arg;
            
            pjd.getProfil().addProject(new Project(wVersion, wProjectName));
            pjd.getManager().overwhriteXML();
            
            System.out.println(String.format("%s %s %d recovered",wWho,wProjectName,wVersion));
        }
    }
    
}
