package gr.val.util;

public class ValUtils {

    public static String finalizeString(String value) {
        return finalizeString("", value);
    }

    public static String finalizeString(String prefix, String value) {
        value = value.replaceFirst(prefix, "");
        value = value.trim();
        return value.replace("'", "''");
    }
}
