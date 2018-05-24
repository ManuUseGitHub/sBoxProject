/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient;

/**
 *
 * @author tonioush
 */
public class TaskAnalyser {

    //private static final String ACCENTUES = "àâéèëîïôöùûüçœÅâÂÀÃÂÉÈÊËÎÏÔÖÙÛÜÇŒ";
    protected static final String NUMERIC = "0-9";
    protected static final String ALPHA = "a-zA-Z";
    protected static final String ESPACE = "\\ ";

    protected static final String ALPHA_NUMERIC = ALPHA + NUMERIC; // lettre_chiffre
    protected static final String CRLF = "[\\x0D\\x0A]*";// \d13\d10 ou \r\n

    protected static final String MESSAGE = "[" + ALPHA_NUMERIC + ESPACE + "]{1,200}";

    //le message ne doit pas excéder plus de 200 caractère
    protected static final String MALFORMED_MESSAGE = "[" + ALPHA_NUMERIC + ESPACE + "]{201,}";
    protected static final String MESSAGE_CODE = "[C]([" + NUMERIC + "]{1,3})";

    public static final String ERR = "^ERR (?:(" + MESSAGE + "|(?:)))$";

    public static final String OK = "^OK (?:(" + MESSAGE + "|(?:)))$";
    public static final String OK_WITH_QUIT = "^OK (?i)(QUIT)$";

    public static final String ERRWITHCODE = "^ERR (?:" + MESSAGE_CODE + " (" + MESSAGE + "|(?:)))$";
    public static final String OKWITHCODE = "^OK (?:" + MESSAGE_CODE + " (" + MESSAGE + "|(?:)))$";

    protected static final String ID = "[" + ALPHA_NUMERIC + "\\-\\_]{2,10}";
    protected static final String VERSION_ID = "[" + NUMERIC + "]{1,3}";

    public String analyse(String message) {

        if (!message.matches(MALFORMED_MESSAGE) && message.matches(ERRWITHCODE)) {
            return "BAD_WITH_CODE_INSIDE";
        } else if (message.matches(ERR)) {
            return "BAD";
        } else if (!message.matches(MALFORMED_MESSAGE) && message.matches(OKWITHCODE)) {
            return "GOOD_WITH_CODE_INSIDE";
        } else if (message.matches(OK_WITH_QUIT)) {
            return "GOOD_QUIT";
        } else if (message.matches(OK)) {
            return "GOOD";
        } else {
            return "NOT GOOD NOT BAD \"" + message + "\"";
        }
    }
}
