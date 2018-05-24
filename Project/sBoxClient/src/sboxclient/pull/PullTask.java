/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.pull;

import sboxclient.nextOkActionable.NextOkActionnableTask;

/**
 *
 * @author MAZE2
 */
public class PullTask extends NextOkActionnableTask{

    private String destination;
    private String Id;

    public void setValues(String pullId, String pullDestination) {
        this.Id = pullId;
        this.destination = pullDestination;
    }

    public String getDestination() {
        return destination;
    }

    public String getId() {
        return Id;
    }
}
