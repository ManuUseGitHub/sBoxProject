/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.RECOVER;

import node.notImplementation.base.TaskAnalyser;

/**
 *
 * @author MAZE2
 */
class RecoverTaskAnalyser extends TaskAnalyser {

    public static String RECOVER = String.format("^RECOVER$");

    @Override
    public String analyse(String message) {
        if (message.matches(RECOVER)) {
            return "RETABLIR";
        }
        return "NONE";
    }

}
