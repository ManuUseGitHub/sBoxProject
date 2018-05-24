package node;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author MAZE2
 */
public class TaskParser {

    private Matcher parse(String message, String patern) {
        Pattern pattern = Pattern.compile(patern);
        Matcher matcher = pattern.matcher(message);
        matcher.find();
        return matcher;
    }

    public String parseOneGroup(String message, String patern) {
        Matcher matcher = parse(message, patern);
        return matcher.group(1);
    }

    public String[] parseMultipleGroups(String message, String patern) {
        Matcher matcher = parse(message, patern);
        int count = matcher.groupCount();
        String[] captured = new String[count];
        for (int i = 0; i < count; ++i) {
            captured[i] = matcher.group(i+1);
        }
        return captured;
    }
}
