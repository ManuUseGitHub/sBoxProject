package Stepping.implementations;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MAZE2
 */
public class ConsoleClearer {

    private final RememberAllWrittenTextPrintStream remember;
    private static final ConsoleClearer INSTANCE = new ConsoleClearer();
    
    private ConsoleClearer() {
        remember = new RememberAllWrittenTextPrintStream(System.out);
    }

    public void clearLastLine() {
        //http://stackoverflow.com/questions/4906248/get-console-text-in-java
        
        clearConsole();
        remember.deleteLastLine();
        getRemember().replaceText();
    }

    private void clearConsole() {
        //http://stackoverflow.com/questions/19252496/clear-screen-with-windows-cls-command-in-java-console-application
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ConsoleClearer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the INSTANCE
     */
    public static ConsoleClearer getINSTANCE() {
        return INSTANCE;
    }

    /**
     * @return the remember
     */
    public RememberAllWrittenTextPrintStream getRemember() {
        return remember;
    }
}
