package to.us.resume_builder.business.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringTemplateTester {

    @Test
    public void testClone() {
        StringTemplate st = new StringTemplate("template");
        assertNotEquals(st, st.clone());
    }

    @Test
    public void testToString() {
        assertEquals("template", new StringTemplate("template").toString());
    }
}
