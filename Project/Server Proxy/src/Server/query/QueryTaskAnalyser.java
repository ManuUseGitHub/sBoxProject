/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.query;

import Server.notimpl.base.TaskAnalyser;

/**
 *
 * @author MAZE2
 */
public class QueryTaskAnalyser extends TaskAnalyser {

    private static final String QUERRY = "^QUERY$";
    public static final String WHOSE_QUERY = "^(?i)(?:Query)([a-zA-Z0-9]{2,30})$";

    @Override
    public String analyse(String message) {
        if (message.matches(QUERRY)) {
            return "DEMANDE";
        } else if (message.matches(WHOSE_QUERY)) {
            return "REQUETE_PERSONALISEE";
        }
        return "NONE";
    }

}
