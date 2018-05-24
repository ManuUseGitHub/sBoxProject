/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.RESTORE;

import util.Parser;

/**
 *
 * @author MAZE2
 */
public class RestoreTaskParser extends Parser{
    public String[] parseInfo(String message){
        return parseMultipleGroups(message, RestoreTaskAnalyser.RESTORE);
    }
}
