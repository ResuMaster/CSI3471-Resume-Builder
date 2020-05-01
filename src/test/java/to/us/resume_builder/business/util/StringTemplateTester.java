package to.us.resume_builder.business.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests methods in the {@link StringTemplate} class
 * @author Brooklynn Stone
 */
public class StringTemplateTester {

    /**
     * Tests that the clone of a {@link StringTemplate} points to a deepy copy of the object
     */
    @Test
    public void testClone() {
        StringTemplate st = new StringTemplate("template");
        assertNotEquals(st, st.clone());
    }

    /**
     * Tests that the toString method returns the correct name given an input value
     */
    @Test
    public void testToString() {
        assertEquals("template", new StringTemplate("template").toString());
    }
}
