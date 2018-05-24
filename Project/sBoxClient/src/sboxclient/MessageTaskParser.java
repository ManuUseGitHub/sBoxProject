/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author MAZE2
 */
public class MessageTaskParser extends TaskParser{

    public static String parseOK(String message) {
        return captureSingle(TaskAnalyser.OK, message);
    }

    public static String parseERR(String message) {
        return captureSingle(TaskAnalyser.ERR, message);
    }

    public static String[] parseERR_WITH_CODE(String message) {
        Matcher matcher = capturedMatch(TaskAnalyser.ERRWITHCODE, message);
        try {
            return new String[]{matcher.group(1), matcher.group(2)};
        } catch (IllegalStateException ex) {
            return null;
        }
    }

    public static String[] parseOK_WITH_CODE(String message) {
        Matcher matcher = capturedMatch(TaskAnalyser.OKWITHCODE, message);
        try {
            return new String[]{matcher.group(1), matcher.group(2)};
        } catch (IllegalStateException ex) {
            return null;
        }
    }
}
