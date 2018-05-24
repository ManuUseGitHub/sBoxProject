/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.ALIVE;

import Server.notimpl.base.TaskAnalyser;

/**
 *
 * @author MAZE2
 */
public class AliveTaskAnalyser extends TaskAnalyser{

    public static final String ALIVE = "^ALIVE ([0-9]{1,3})$";

    public String analyse(String message) {
        if (message.matches(ALIVE)) {
            return "VIVANT";
        }
        return "NONE";
    }
}
