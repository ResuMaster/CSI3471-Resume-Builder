package to.us.resume_builder.business.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests method in the MiscUtils class
 * @author Brooklynn Stone
 */
public class MiscUtilsTester {

    /**
     * Tests that two calls to randomAlphaNumericString generate two different Strings
     */
    @Test
    public void testRandomAlphanumericString() {
        assertNotEquals(MiscUtils.randomAlphanumericString(10), MiscUtils.randomAlphanumericString(10));
    }

    /**
     * Tests that escapeLaTeX generates the correct escape characters with only special characters
     */
    @Test
    public void testEscapeLaTeX() {
        assertEquals("\\&\\$\\{", MiscUtils.escapeLaTeX("&${"));
    }

    /**
     * Tests that escapeLaTeX generates the correct escape characters with special and non-special characters
     */
    @Test
    public void testEscapeLaTeX2() {
        assertEquals("\\&\\$\\{WORD\\}", MiscUtils.escapeLaTeX("&${WORD}"));
    }
}
