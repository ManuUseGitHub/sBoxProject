/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.notImplementation.base;

import java.util.Observable;
import java.util.Observer;
import node.notImplementation.Network;

/**
 *
 * @author MAZE2
 */
public abstract class Controller implements Observer {

    private String message;
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Network) {
            message = ((Network) o).getMessage();
        }
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
