/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.pull;

import java.util.Observable;

/**
 *
 * @author MAZE2
 */
public class EndPullProcess extends Observable {

    public void pullAfterAll() {
        setChanged();
        notifyObservers("TERMINATED");
    }
}
