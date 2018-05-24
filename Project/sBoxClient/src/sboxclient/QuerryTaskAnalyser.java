/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient;

/**
 *
 * @author MAZE2
 */
public class QuerryTaskAnalyser extends TaskAnalyser{
    public static final String LIST_PROJECTS = "^LIST ((?:"+ID+"\\:"+VERSION_ID+"\\;)*)";
    public static final String LABELED_PROJECT = "^("+ID+") -- ["+ALPHA_NUMERIC+"]* ("+VERSION_ID+")$";
    
    @Override
    public String analyse(String message){
        String analysed = super.analyse(message);
        if(analysed.matches("^NOT GOOD NOT BAD.*$")){
            if(message.matches(LIST_PROJECTS)){
                analysed = "DEMANDE";
            }
        }
        return analysed;
    }
}
