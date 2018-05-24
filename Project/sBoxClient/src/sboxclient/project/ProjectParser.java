/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.project;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import sboxclient.QuerryTaskAnalyser;
import sboxclient.TaskParser;

/**
 *
 * @author MAZE2
 */
public class ProjectParser extends TaskParser {

    public static String formatSelectedProject(String message) {
        Matcher matcher = capturedMatch(QuerryTaskAnalyser.LABELED_PROJECT, message);
        return String.format("%s %s",matcher.group(1),matcher.group(2));
    }

    public static String[] parseProjects(String message) {
        Matcher matcher = capturedMatch(QuerryTaskAnalyser.LIST_PROJECTS, message);
        try {
            StringReader reader = new StringReader(matcher.group(1));
            return readProjects(reader);

        } catch (IllegalStateException ex) {
            return null;
        }
    }

    private static String[] readProjects(StringReader reader) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> temp = new ArrayList<>();
        try (Reader r = reader) {
            int read;
            while ((read = r.read()) >= 0) {
                switch (read) {
                    case ';':
                        temp.add(sb.toString());
                        sb.setLength(0);
                        break;
                    case ':':
                        sb.append(" -- version ");
                        break;
                    default:
                        sb.append((char) read);
                        break;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ProjectParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[] resultList = new String[temp.size()];
        return temp.toArray(resultList);
    }
}
