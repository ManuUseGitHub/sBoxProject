/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.INFO;

import node.notImplementation.base.TaskAnalyser;

/**
 *
 * @author MAZE2
 */
class InfoTaskAnalyser extends TaskAnalyser {

    public static String INFO = String.format("^INFO (%s) (%s) (%s)$",WHO, ID, VERSION_ID);

    @Override
    public String analyse(String message) {
        if (message.matches(WHOIS)) {
            return "WHOIS";
        }
        else if (message.matches(INFO)) {
            return "INFO";
        }
        return "NONE";
    }

}
