package node.notImplementation.base;

/**
 *
 * @author tonioush
 */
public abstract class TaskAnalyser {
    protected static final String ACCENTUES = "àâéèëîïôöùûüçœÅâÂÀÃÂÉÈÊËÎÏÔÖÙÛÜÇŒ";
    protected static final String NUMERIC = "0-9";
    protected static final String ALPHA = "a-zA-Z";
    protected static final String ESPACE = " ";

    protected static final String ALPHA_NUMERIC = ALPHA + NUMERIC; // lettre_chiffre
    protected static final String CRLF = "[\\x0D\\x0A]*";// \d13\d10 ou \r\n
    protected static final String WHO = "[" + ALPHA_NUMERIC + "]{2,30}";
    protected static final String ID = "[" + ALPHA_NUMERIC + "\\-\\_]{2,10}";
    protected static final String VERSION_ID = "[" + NUMERIC + "]{1,3}";
    protected static final String PROJECT = "(" + ID + ") (" + VERSION_ID + ")";

    protected static final String MESSAGE = "[" + ALPHA_NUMERIC + ESPACE + "]{1,200}";
    protected static final String ERR = "^ERR (" + MESSAGE + "|(?:))$";
    protected static final String OK = "^OK (" + MESSAGE + "|(?:))$";
    public static final String OK_WITH_SIZE ="^OK ([0-9]*)$";
    public static final String WHOIS = "^WHOIS ([" + ALPHA_NUMERIC + "]{2,30})$";
    public final static String PORT = "[0-9]{1,5}";

    protected static final String QUIT = "^(QUIT)$";

    public abstract String analyse(String message);

}
