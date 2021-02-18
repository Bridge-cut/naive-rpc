package top.naive_projects.rpc.common.utils;

import java.util.regex.Pattern;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/8 下午12:09
 */
public class StringUtils {

    private static final Pattern INT_PATTERN = Pattern.compile("^-*\\d+$");

    public static String object2string(Object object) {
        return object.toString();
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public static boolean isInteger(String string) {
        return isNotEmpty(string) && INT_PATTERN.matcher(string).matches();
    }

    public static Integer parseInteger(String string) {
        return isInteger(string) ? Integer.parseInt(string) : 0;
    }

    public static String substring(String openToken, String closeToken, String string) {
        int openTokenIndex = string.indexOf(openToken) + openToken.length();
        return substring(openTokenIndex, closeToken, string);
    }

    public static String substring(String closeToken, String string) {
        return substring(0, closeToken, string);
    }

    public static String substring(Integer openTokenIndex, String closeToken, String string) {
        if (closeToken.equals("")) return string.substring(openTokenIndex);

        return string.substring(openTokenIndex, string.indexOf(closeToken, openTokenIndex));
    }

    public static String greedSubstring(Integer openTokenIndex, String closeToken, String string) {
        return string.substring(openTokenIndex, string.lastIndexOf(closeToken));
    }

    public static String lastSubstring(String openToken, String closeToken, String string) {
        return substring(string.lastIndexOf(openToken) + openToken.length(), closeToken, string);
    }
}
