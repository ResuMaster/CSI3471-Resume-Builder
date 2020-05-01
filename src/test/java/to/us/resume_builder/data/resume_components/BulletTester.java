package to.us.resume_builder.data.resume_components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import to.us.resume_builder.business.export_LaTeX.ResumeTemplate;
import to.us.resume_builder.data.resume_components.category.BulletCategory;
import to.us.resume_builder.data.resume_components.category.CategoryType;

/**
 * Test class for the {@link Bullet}
 * @author Brooklynn Stone
 */
public class BulletTester {
    /**
     * {@link Resume} object which will hold a {@link BulletCategory} with the {@link Bullet}
     * to perform tests on
     */
    private static Resume r = null;
    /**
     * The {@link BulletCategory} holding the {@link Bullet} to perform tests on
     */
    private static BulletCategory bc = null;
    /**
     * The {@link Bullet} to perform tests on
     */
    private static Bullet b = null;

    /**
     * Initializes a Bullet with test data
     */
    @BeforeEach
    public void init() {
        r = new Resume();
        bc = new BulletCategory(r.createCategory(CategoryType.BULLETS));
        b = new Bullet(bc.addBullet());
        b.setText("This is a bullet.");
    }

    /**
     * Test getter for text of a bullet
     */
    @Test
    public void testGetText() {
        assertEquals("This is a bullet.", b.getText());
    }

    /**
     * Test setter for a Bullet
     */
    @Test
    public void testSetText() {
        b.setText("This is not a bullet.");
        assertEquals("This is not a bullet.", b.getText());
    }

    /**
     * Verify correct LaTeX String generated
     */
    @Test
    public void testFormatLaTeXString() {
        assertEquals("\\item This is a bullet.\n", b.formatLaTeXString(ResumeTemplate.DEFAULT));
    }
}
