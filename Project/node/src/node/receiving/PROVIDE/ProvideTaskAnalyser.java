/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.PROVIDE;

import node.notImplementation.base.TaskAnalyser;

/**
 *
 * @author MAZE2
 */
public class ProvideTaskAnalyser extends TaskAnalyser{

    public final static String PROVIDE = "^PROVIDE ("+WHO+") ("+ID+") ("+VERSION_ID+") ("+PORT+")$";
    @Override
    public String analyse(String message) {
        if(message.matches(PROVIDE)){
            return "GERE";
        }
        return "NONE";
    }
    
}
