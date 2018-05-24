/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.RESTORE;

import Server.notimpl.base.TaskAnalyser;

/**
 *
 * @author MAZE2
 */
class RestoreTaskAnalyser extends TaskAnalyser {

    public static String RESTORE = String.format("^RESTORE (%s) (%s) (%s)$",WHO, ID, VERSION_ID);

    @Override
    public String analyse(String message) {
        if (message.matches(RESTORE)) {
            return "RESTORATION";
        }
        return "NONE";
    }

}
