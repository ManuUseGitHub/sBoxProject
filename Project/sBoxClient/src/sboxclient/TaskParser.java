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
public class TaskParser {
    protected static String captureSingle(String patern, String message) {
        return capturedMatch(patern, message).group(1);
    }

    protected static Matcher capturedMatch(String patern, String message) {
        Pattern pattern = Pattern.compile(patern);
        Matcher matcher = pattern.matcher(message);
        try {
            matcher.find();
        } catch (IllegalStateException e) {
        }

        return matcher;
    }
}
