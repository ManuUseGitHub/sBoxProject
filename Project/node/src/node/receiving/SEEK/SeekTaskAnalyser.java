/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.SEEK;

import node.notImplementation.base.TaskAnalyser;

/**
 *
 * @author MAZE2
 */
class SeekTaskAnalyser extends TaskAnalyser {

    public final static String PORT = "[0-9]{1,5}";
    public static String SEEK = String.format("^SEEK (%s)$",PORT);

    @Override
    public String analyse(String message) {
        if (message.matches(SEEK)) {
            return "SONDE";
        }
        return "NONE";
    }

}
