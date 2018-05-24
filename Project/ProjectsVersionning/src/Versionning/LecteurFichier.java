package Versionning;

import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author MAZE2
 */
public abstract class LecteurFichier {
    @SuppressWarnings("StringBufferMayBeStringBuilder")// procéder caratère par caractère
    protected String readLine(Reader reader) throws IOException {
        int car;
        StringBuffer buff = new StringBuffer();
        while ((car = reader.read()) != -1 && (char) car != '\n') {   // lecture charactère par charactère
            buff.append((char) car);
        }
        if (buff.length() == 0 && car == -1) {
            return null;
        }
        return buff.toString().trim();
    }
}
