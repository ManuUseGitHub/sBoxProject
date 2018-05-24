/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.create;

import sboxclient.nextOkActionable.NextOkActionnableTask;

/**
 *
 * @author MAZE2
 */
public class CreateTask extends NextOkActionnableTask {

    private String projectName;

    public CreateTask() {
        
    }
    public void setValues(String name) {
        projectName = name;
    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }
}
