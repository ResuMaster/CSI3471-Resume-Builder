package to.us.resume_builder.data.resume_components.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import to.us.resume_builder.business.export_LaTeX.ResumeTemplate;
import to.us.resume_builder.data.resume_components.Resume;

import static org.junit.Assert.*;

/**
 * Test class for the {@link TextCategory}
 * @author Brooklynn Stone
 */
public class TextCategoryTester extends CategoryTester {
    /**
     * Test resume which generates an id for the {@link BulletCategory}
     */
    private static Resume r;

    /**
     * Test {@link TextCategory} to verify set, get, and LaTeX generation
     */
    private static TextCategory tc;

    /**
     * Initializes the {@link Resume} and the {@link TextCategory} with "The Text"
     */
    @BeforeEach
    public void init() {
        r = new Resume();
        tc = new TextCategory(r.createCategory(CategoryType.BULLETS));
        tc.setText("The text");
        tc.setDisplayName("Display name");
        tc.setName("name");
    }

    /**
     * Verify getType returns a Text CategoryType
     */
    @Test
    @Override
    void testGetType() {
        assertEquals(CategoryType.HEADER, tc.getType());
    }

    /**
     * Testing get and set display name
     */
    @Test
    @Override
    void testDisplayName() {
        assertEquals("Display name", tc.getDisplayName());
    }

    /**
     * Testing get and set name
     */
    @Test
    @Override
    void testName() {
        assertEquals("name", tc.getName());
    }

    /**
     * Sets the text to something else and verifies that this change has been made internally
     */
    @Test
    public void testSetText() {
        tc.setText("This is the text");
        assertEquals("This is the text", tc.getText());
    }

    /**
     * Verifies the text is being stored correctly internally
     */
    @Test
    public void testGetText() {
        assertEquals("The text", tc.getText());
    }

    /**
     * Sets the text to something else and verifies that this text is returned as expected
     */
    @Test
    public void testGetText2() {
        tc.setText("More text");
        assertEquals("More text", tc.getText());
    }

    /**
     * Verify that the LaTeX string is generated in the expected manner
     */
    @Test
    public void testFormatLaTeXString() {
        tc.setText("This is the text");

        assertEquals("\\resumetextcategory{}{This is the text}\n\n", tc.formatLaTeXString(ResumeTemplate.DEFAULT));
    }
}
