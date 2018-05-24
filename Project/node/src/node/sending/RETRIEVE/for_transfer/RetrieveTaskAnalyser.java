package node.sending.RETRIEVE.for_transfer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import node.notImplementation.base.TaskAnalyser;

/**
 *
 * @author MAZE2
 */
public class RetrieveTaskAnalyser extends TaskAnalyser {
    public static final String RETRIEVE = "^RETRIEVE ("+WHO+") "+PROJECT+"$";
    @Override
    public String analyse(String message) {
        if(message.matches(RETRIEVE)) {
            return "RECUPERER";
        }
        return "NONE";
    }

}
