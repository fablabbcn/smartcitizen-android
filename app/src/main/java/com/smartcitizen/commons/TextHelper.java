package com.smartcitizen.commons;

/**
 * Created by ferran on 08/07/15.
 */
public class TextHelper {

    public static String appendIterable(Iterable<String> iterable, String separator) {
        if (iterable == null)
            return null;

        StringBuilder sb = new StringBuilder();

        for (String item : iterable) {
            sb.append(item);
            sb.append(separator);
        }

        if (sb.length() > 0) {
            sb.delete(sb.length() - separator.length(), sb.length());
        }

        return sb.toString();
    }

    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    /*public static float extractFloat(String text) {
        Pattern p = Pattern.compile("[\\-]?(([0-9]+\\.[0-9]*)|([0-9]+))");
        Matcher m = p.matcher(text);
        if (m.find()) {
            return Float.parseFloat(m.group());
        }

        return 0.0f;
    }*/
}
