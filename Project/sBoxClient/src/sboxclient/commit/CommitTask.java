/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.commit;

import sboxclient.nextOkActionable.NextOkActionnableTask;

/**
 *
 * @author MAZE2
 */
public class CommitTask extends NextOkActionnableTask{
    
    private final String sourceFolder;
    private final String id;

    public CommitTask(String sourceFolder, String id) {
        this.sourceFolder = sourceFolder;
        this.id = id;
    }

    /**
     * @return the sourceFolder
     */
    public String getSourceFolder() {
        return sourceFolder;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }    
}
