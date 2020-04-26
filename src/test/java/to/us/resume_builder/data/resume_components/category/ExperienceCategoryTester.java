package to.us.resume_builder.data.resume_components.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import to.us.resume_builder.business.export_LaTeX.ResumeTemplate;
import to.us.resume_builder.data.resume_components.Bullet;
import to.us.resume_builder.data.resume_components.Experience;
import to.us.resume_builder.data.resume_components.Resume;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for the {@link ExperienceCategory}
 * @author Ashley Lu Couch
 */
public class ExperienceCategoryTester extends CategoryTester {

    private static Resume r;
    private static Category ec;
    private static List<Experience> experience;

    /**
     * Initializes the resume
     */
    @Override
    public void init() {
        r = new Resume();
        ec = new ExperienceCategory(r.createCategory(CategoryType.EXPERIENCE));
    }

    /**
     * Abstract getType checks to make sure the type of the category is expected
     * value
     */
    @Override
    void testGetType() {
        assertEquals(CategoryType.EXPERIENCE, ec.getType());
        assertEquals(ec.getType(), r.getCategoryList().get(0).getType());
    }

    /**
     * Verifies that set and get DisplayName work
     */
    @Override
    void testDisplayName() {
        ec.setName("this is the name");
        assertEquals("this is the name", r.getCategoryList().get(0).getName());
    }

    /**
     * Verifies that set and get Name work
     */
    @Override
    void testName() {

    }
}
