package to.us.resume_builder.business.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MiscUtilsTester {

    @Test
    public void testRandomAlphanumericString() {
        assertNotEquals(MiscUtils.randomAlphanumericString(10), MiscUtils.randomAlphanumericString(10));
    }

    @Test
    public void testEscapeLaTeX() {
        assertEquals("\\&\\$\\{", MiscUtils.escapeLaTeX("&${"));
    }

    @Test
    public void testEscapeLaTeX2() {
        assertEquals("\\&\\$\\{WORD\\}", MiscUtils.escapeLaTeX("&${WORD}"));
    }
}
