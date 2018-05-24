/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.received.WHOHAS;

import node.notImplementation.base.TaskAnalyser;

/**
 *
 * @author MAZE2
 */
public class WhohasTaskAnalyser extends TaskAnalyser{
    public static final String WHOHAS = "^WHOHAS ("+WHO+") "+PROJECT+"$";
    
    @Override
    public String analyse(String message) {
        if(message.matches(WHOHAS)){
            return "QUI_A";
        }
        return "NONE";
    }
    
}
