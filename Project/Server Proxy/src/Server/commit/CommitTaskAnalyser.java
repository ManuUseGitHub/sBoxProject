/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.commit;

import Server.notimpl.base.TaskAnalyser;

/**
 *
 * @author MAZE2
 */
public class CommitTaskAnalyser extends TaskAnalyser{
    private static final String START_BY_COMMIT = "^COMMIT.*$";
    private static final String CONTINUE_BY_ID = "^COMMIT " + ID + " .*$";
    
    private static final String ZIP_FILE = "(^(?:[A-Za-z0-9+\\/]{4})*(?:[A-Za-z0-9+\\/]{2}\\\\=\\\\=|[A-Za-z0-9+\\/]{3}\\\\=)?$)|(^###$)";
    
    public static final String COMMIT = "^COMMIT "+PROJECT+"$";
    
    @Override
    public String analyse(String message) {
        if (message.matches(ERR)) {
            return "ERREUR";
        } else if (message.matches(START_BY_COMMIT)) {
            return onParseCommit(message);
        } else if (message.matches(OK)) {
            return "OK";
        } else if (message.matches(QUIT)) {
            return "BYE";
        } else if (message.matches(ZIP_FILE)) {
            return "ZIP";
        }
        return "NONE";
    }

    private String onParseCommit(String message) {
        if (message.matches(CONTINUE_BY_ID)) {
            return message.matches(COMMIT) ? "COMMIT" : "COMMIT_VERSION_ERROR";
        } else {
            return "COMMIT_ID_ERROR";
        }
    }
}
