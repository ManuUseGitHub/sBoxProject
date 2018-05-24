/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.util;

import sboxclient.TaskAnalyser;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MAZE2
 */
public class TaskAnalyserTest {

    public TaskAnalyserTest() {
    }

    @Test
    public void testSomeMethod() {
        TaskAnalyser analyser = new TaskAnalyser();

        String result = analyser.analyse("ERR C12 La");
        assertEquals("BAD_WITH_CODE_INSIDE", result);
    }
    @Test
    public void testSomeMethod2() {
        TaskAnalyser analyser = new TaskAnalyser();
        System.out.println(TaskAnalyser.OKWITHCODE);
        String result = analyser.analyse("OK C1 La");
        assertEquals("GOOD_WITH_CODE_INSIDE", result);
    }
    
    @Test
    public void testSomeMethod23() {
        TaskAnalyser analyser = new TaskAnalyser();

        String result = analyser.analyse("OK C1 ");
        assertEquals("GOOD_WITH_CODE_INSIDE", result);
        
        result = analyser.analyse("OK C1");
        assertEquals("GOOD_WITH_CODE_INSIDE", result);
        
        result = analyser.analyse("OK ");
        assertEquals("GOOD", result);
    }
    
    @Test
    public void testSomeMethod4() {
        TaskAnalyser analyser = new TaskAnalyser();

        String result = analyser.analyse("ERR C1 ");
        assertEquals("BAD_WITH_CODE_INSIDE", result);
        
        result = analyser.analyse("ERR C1");
        assertEquals("BAD_WITH_CODE_INSIDE", result);
        
        result = analyser.analyse("ERR ");
        assertEquals("BAD", result);
    }

    @Test
    public void aMalformedErrWithCodeISASimpleError() {
        TaskAnalyser analyser = new TaskAnalyser();

        String result = analyser.analyse("ERR L12La");
        assertEquals("BAD", result);

    }

    @Test
    public void emptyStringDoesntMAtch() {
        TaskAnalyser analyser = new TaskAnalyser();
        boolean[] conditions = new boolean[]{
            "[a-zA-Z0-9àâéèëîïôöùûüçœÅâÂÀÃÂÉÈÊËÎÏÔÖÙÛÜÇŒ\\ ]{201,}".matches(""),
            "^ERR (?:[C]([0-9]{1,3}) ([a-zA-Z0-9àâéèëîïôöùûüçœÅâÂÀÃÂÉÈÊËÎÏÔÖÙÛÜÇŒ\\ ]{1,200})|())$".matches("")};
        assertFalse(conditions[0]);
        assertFalse(conditions[1]);
        assertFalse(conditions[0] && conditions[1]);
    }
    
    

}
