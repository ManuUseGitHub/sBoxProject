package node.notImplementation.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author MAZE2
 */
public abstract class Parser {
    public Matcher parse(String message,String patern) {
        Pattern pattern = Pattern.compile(patern);
        Matcher matcher = pattern.matcher(message);
        matcher.find();
        return matcher;
    }
}
