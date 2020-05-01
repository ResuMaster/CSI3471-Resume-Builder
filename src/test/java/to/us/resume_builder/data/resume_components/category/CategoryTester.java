package to.us.resume_builder.data.resume_components.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import to.us.resume_builder.data.resume_components.Resume;

/**
 * Test class for the {@link Category}
 *
 * @author Ashley Lu Couch
 */
public abstract class CategoryTester {
    private static Resume r;
    private static String cid;

    /**
     * Initializes the resume
     */
    @BeforeEach
    public void init() {
        r = new Resume();
    }

    /**
     * Abstract getType checks to make sure the type of the category is expected
     * value
     */
    @Test
    abstract void testGetType();

    /**
     * Verifies that set and get DisplayName work
     */
    @Test
    abstract void testDisplayName();

    /**
     * Verifies that set and get Name work
     */
    @Test
    abstract void testName();

}
