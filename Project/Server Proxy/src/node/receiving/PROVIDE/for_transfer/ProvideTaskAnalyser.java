/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.PROVIDE.for_transfer;

import Server.notimpl.base.TaskAnalyser;

/**
 *
 * @author MAZE2
 */
public class ProvideTaskAnalyser extends TaskAnalyser{

    public final static String PORT = "[0-9]{1,5}";
    public final static String PROVIDE = "^PROVIDE ("+WHO+") ("+ID+") ("+VERSION_ID+") ("+PORT+")$";
    @Override
    public String analyse(String message) {
        if(message.matches(PROVIDE)){
            return "GERE";
        }
        return "NONE";
    }
    
}
