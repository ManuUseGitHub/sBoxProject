/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.INFO;

import java.util.regex.Matcher;
import node.notImplementation.base.Parser;

/**
 *
 * @author MAZE2
 */
public class InfoTaskParser extends Parser{
    public String[] parseInfo(String message){
        Matcher matcher = parse(message, InfoTaskAnalyser.INFO);
        return new String[]{matcher.group(1),matcher.group(2),matcher.group(3)};
    }
}
