package node.sending.RETRIEVE;

import Server.notimpl.base.TaskAnalyser;

/**
 *
 * @author MAZE2
 */
public class RetrievetTaskAnalyser extends TaskAnalyser {

    private static final String ZIP_FILE = "(^(?:[A-Za-z0-9+\\/]{4})*(?:[A-Za-z0-9+\\/]{2}\\\\=\\\\=|[A-Za-z0-9+\\/]{3}\\\\=)?$)|(^###$)";

    @Override
    public String analyse(String message) {
        if (message.matches(ERR)) {
            return "ERREUR";
        } else if (message.matches(OK_WITH_SIZE)) {
            return "TAILLE";
        } else if (message.matches(OK)) {
            return "OK";
        } else if (message.matches(QUIT)) {
            return "BYE";
        } else if (message.matches(ZIP_FILE)) {
            return "ZIP";
        }
        return "NONE";
    }
}
