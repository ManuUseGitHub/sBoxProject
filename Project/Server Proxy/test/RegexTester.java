/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import util.Parser;
import Server.notimpl.base.TaskAnalyser;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MAZE2
 */
public class RegexTester {

    private final Parser taskParser;

    public RegexTester() {
        taskParser = new Parser();
    }

    @Test
    public void WhoQueryContainsWhoisAbout() {
        String whoPatern = "^(?i)(?:Query)([a-zA-Z0-9]{2,30})$";
        String who = "QueryAntoine";
        String[] matched = taskParser.parseMultipleGroups(who, whoPatern);
        assertEquals(matched[0],"Antoine");
    }

}
