package to.us.resume_builder.util;

import java.util.Random;

/**
 * This class contains miscellaneous utility functions used throughout the
 * program.
 */
public final class MiscUtils {
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
}
