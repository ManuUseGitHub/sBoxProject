/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.notimpl.base.ProjectModel;

/**
 *
 * @author MAZE2
 */
public class ForWhoseProjectModel extends ProjectModel {
    private final String whose;

    public ForWhoseProjectModel(String who,String projectName, String ProjectVersion) {
        super(projectName,ProjectVersion);
        this.whose = who;
    }
    
    public ForWhoseProjectModel(String parsed[]){
        super(parsed[1],parsed[2]);
        this.whose = parsed [0];
    }
    
    public String getWhose() {
        return whose;
    }
}
