/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.create;

import Server.notimpl.base.TaskAnalyser;

/**
 *
 * @author MAZE2
 */
public class CreateTaskAnalyser extends TaskAnalyser {

    private static final String CONTAINSCREATE = "^CREATE .*$";
    public static final String CREATE = "^CREATE (" + ID + ")$";

    public String analyse(String message) {
        if (message.matches(ERR)) {
            return "ERREUR";
        } else if (message.matches(CONTAINSCREATE)) {
            return onParseCreate(message);
        } else if (message.matches(OK)) {
            return "OK";
        } else if (message.matches(QUIT)) {
            return "BYE";
        }
        return "NONE";
    }

    private String onParseCreate(String message) {
        return message.matches(CREATE) ? "CREATE" : "CREATE_ID_ERROR";
    }
}
