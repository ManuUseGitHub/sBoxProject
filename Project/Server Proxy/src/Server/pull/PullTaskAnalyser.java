/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.pull;

import Server.notimpl.base.TaskAnalyser;

/**
 *
 * @author MAZE2
 */
public class PullTaskAnalyser extends TaskAnalyser {

    private static final String CONTAINS_PULL = "^PULL .*$";
    private static final String CONTINUE_BY_ID = "^PULL (" + ID + ") .*$";
    public static final String PULL = "^PULL "+PROJECT+"$";

    private static final String ZIP_FILE = "(^(?:[A-Za-z0-9+\\/]{4})*(?:[A-Za-z0-9+\\/]{2}\\\\=\\\\=|[A-Za-z0-9+\\/]{3}\\\\=)?$)|(^###$)";
    public static String PULLED = "OK Pulled";

    public String analyse(String message) {
        if (message.matches(ERR)) {
            return "ERREUR";
        } else if (message.matches(CONTAINS_PULL)) {
            return onParsePull(message);
        }else if(message.matches(PULLED)){
            return "TIRE";
        } else if (message.matches(OK)) {
            return "OK";
        } else if (message.matches(ZIP_FILE)) {
            return "ZIP";
        } else if (message.matches(QUIT)) {
            return "BYE";
        }
        return "NONE";
    }

    private String onParsePull(String message) {
        if (message.matches(CONTINUE_BY_ID)) {
            return message.matches(PULL) ? "PULL" : "PULL_VERSION_ERROR";
        } else {
            return "PULL_ID_ERROR";
        }
    }
}
