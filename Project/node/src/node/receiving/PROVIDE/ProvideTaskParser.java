/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.PROVIDE;

import java.util.regex.Matcher;
import node.notImplementation.base.Parser;

/**
 *
 * @author MAZE2
 */
public class ProvideTaskParser extends Parser{
    public String[] parseProvide(String message){
        Matcher matcher = super.parse(message, ProvideTaskAnalyser.PROVIDE);
        return new String[]{matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4)};
    }
}
