/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.SEEK;

import java.util.regex.Matcher;
import node.notImplementation.base.Parser;

/**
 *
 * @author MAZE2
 */
public class SeekTaskParser extends Parser{
    public String parseSeek(String message){
        Matcher matcher = super.parse(message, SeekTaskAnalyser.SEEK);
        return matcher.group(1);
    }
}
