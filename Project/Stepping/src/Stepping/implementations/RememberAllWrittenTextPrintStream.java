package Stepping.implementations;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.PrintStream;

/**
 *
 * @author MAZE2
 */
public class RememberAllWrittenTextPrintStream extends PrintStream {

    private static final String newLine = System.getProperty("line.separator");

    private final StringBuilder sb = new StringBuilder();
    private final PrintStream original;

    public RememberAllWrittenTextPrintStream(PrintStream original) {
        super(original);
        this.original = original;
    }

    public void print(double d) {
        sb.append(d);
        original.print(d);
    }

    public void print(String s) {
        sb.append(s);
        original.print(s);
    }

    public void println(String s) {
        sb.append(s).append(newLine);
        original.println(s);
    }

    public void println() {
        sb.append(newLine);
        original.println();
    }

    @Override
    public PrintStream printf(String s, Object... args) {
        sb.append(String.format(s, args));
        original.printf(s, args);
        return original;
    }

    // .....
    // the same for ALL the public methods in PrintStream....
    // (your IDE should help you easily create delegates for the `original` methods.)
    public String getAllWrittenText() {
        return sb.toString();
    }
    
    public void replaceText(){
        String temp = sb.toString();
        System.out.append(temp);
        sb.setLength(0);
        sb.append(temp);
    }

    public void deleteLastLine() {
        int last = sb.lastIndexOf("\n");
        if (last >= 0) {
            sb.delete(last, sb.length());
        }
    }

    public void empty() {
        sb.setLength(0);
    }

}
