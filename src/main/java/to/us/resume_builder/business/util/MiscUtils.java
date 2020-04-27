package to.us.resume_builder.business.util;

import java.util.Random;

/**
 * This class contains miscellaneous utility functions used throughout the
 * program.
 */
public final class MiscUtils {
    /**
     * Get a random alphanumeric string of the specified length.
     *
     * @param length The length of the string to generate.
     *
     * @return The desired random string.
     */
    public static String randomAlphanumericString(int length) {
        int leftLimit = 48;  // Numeral '0'
        int rightLimit = 122; // Letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))  // filter out non-alphanumerics
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    /**
     * Get the LaTeX-escaped version of a string.
     *
     * @param s The string to make safe.
     *
     * @return The LaTeX-escaped version of s.
     */
    public static String escapeLaTeX(String s) {
        if (s == null) {
            return "";
        } else {
            return s
                .replace("&", "\\&")
                .replace("#", "\\#")
                .replace("%", "\\%")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace("_", "\\_")
                .replace("$", "\\$");
        }
    }
}
