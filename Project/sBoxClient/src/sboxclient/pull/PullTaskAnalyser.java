/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.pull;

import sboxclient.TaskAnalyser;

/**
 *
 * @author MAZE2
 */
public class PullTaskAnalyser extends TaskAnalyser{
    private static final String ZIP_FILE = "(^(?:[A-Za-z0-9+\\/]{4})*(?:[A-Za-z0-9+\\/]{2}\\\\=\\\\=|[A-Za-z0-9+\\/]{3}\\\\=)?$)|(^###$)";
    
    @Override
    public String analyse(String message){
        String analysed = super.analyse(message);
        if(analysed.matches("^NOT GOOD NOT BAD.*$")){
            if(message.matches(ZIP_FILE)){
                analysed = "ZIP";
            }
        }
        return analysed;
    }
}
