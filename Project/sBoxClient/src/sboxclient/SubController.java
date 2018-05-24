/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient;

import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import sboxclient.server.SBoxServerClient;

/**
 *
 * @author MAZE2
 */
public abstract class SubController implements Observer {
    private SBoxServerClient client;
    private Observer Queryer;

    public SubController() {
    }
    
    public SubController(Observer Queryer) {
        this.Queryer = Queryer;
    }
    

    /**
     * @return the client
     */
    public SBoxServerClient getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(SBoxServerClient client) {
        this.client = client;
    }
    
    public void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(SubController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the Queryer
     */
    public Observer getQueryer() {
        return Queryer;
    }
}
