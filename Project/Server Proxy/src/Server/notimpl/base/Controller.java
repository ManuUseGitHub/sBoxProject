/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.notimpl.base;

import Server.notimpl.Network;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author MAZE2
 */
public abstract class Controller implements Observer {

    private String message;

    @Override
    public void update(Observable o, Object arg) {
        message = arg != null
                ? message = ((String[]) arg)[0]
                : ((Network) o).getMessage();
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
