package jakwagne.missingpermissions;

import java.util.Arrays;

public class Utils {
    public static final String ACCESS_DENIED = "access denied";
    public static final String DOMAIN_THAT_FAILED = "domain that failed";

    private Utils() {
    }

    public static String[] extractPermission(String line) {
        int begin = line.indexOf('(');
        int end = line.indexOf(')');
        String substring = line.substring(begin + 1, end);
        String[] split = substring.split(" ");
        return Arrays.stream(split).map(s -> s.substring(1, s.length() - 1)).toArray(String[]::new);
    }

    public static String extractCodeSource(String line) {
        int begin = line.indexOf('(');
        int end = line.indexOf(')');
        String substring = line.substring(begin + 1, end);
        return substring.split(" ")[0];
    }

    public static String extractClassLoader(String line) {
        return line.trim();
    }
}
