/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient;

import java.util.Observable;

/**
 *
 * @author MAZE2
 */
public class Task extends Observable{
     private String message ="";

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
        setChanged();
        notifyObservers();
    }
    
    protected void change(){
        this.setChanged();
        this.notifyObservers();
    }
}
