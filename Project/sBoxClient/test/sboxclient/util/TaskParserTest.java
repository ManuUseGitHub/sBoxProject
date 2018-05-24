/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.util;

import sboxclient.MessageTaskParser;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MAZE2
 */
public class TaskParserTest {
    
    public TaskParserTest() {
    }

    @Test
    public void testSomeMethod() {
        String message = "ERR C12 Ceci est un test sur une erreur";
        String [] err_code = MessageTaskParser.parseERR_WITH_CODE(message);
        assertEquals(err_code[0], "12");
        assertEquals(err_code[1], "Ceci est un test sur une erreur");
        assertEquals(MessageTaskParser.parseERR(message), "C12 Ceci est un test sur une erreur");
    }
}
