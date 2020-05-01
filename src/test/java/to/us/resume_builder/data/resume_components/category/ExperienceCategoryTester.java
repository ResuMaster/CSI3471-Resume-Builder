package to.us.resume_builder.data.resume_components.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import to.us.resume_builder.data.resume_components.Experience;
import to.us.resume_builder.data.resume_components.Resume;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link ExperienceCategory}
 *
 * @author Ashley Lu Couch
 */
public class ExperienceCategoryTester extends CategoryTester {

    private static Resume r = null;
    private static ExperienceCategory ec = null;
    private static List<Experience> experience = null;

    /**
     * Initializes the resume
     */
    @Override
    @BeforeEach
    public void init() {
        r = new Resume();
        ec = new ExperienceCategory(r.createCategory(CategoryType.EXPERIENCE));
        experience = new LinkedList<>();
        experience.add(new Experience(ec.addExperience()));
    }

    /**
     * Abstract getType checks to make sure the type of the category is expected
     * value
     */
    @Override
    @Test
    void testGetType() {
        assertEquals(CategoryType.EXPERIENCE, ec.getType());
        assertEquals(ec.getType(), r.getCategoryList().get(0).getType());
    }

    /**
     * Verifies that set and get DisplayName work
     */
    @Override
    @Test
    void testDisplayName() {
        ec.setDisplayName("this is the display name");
        assertEquals("this is the display name", ec.getDisplayName());
    }

    /**
     * Verifies that set and get Name work
     */
    @Override
    @Test
    void testName() {
        ec.setName("this is the name");
        assertEquals("this is the name", ec.getName());
    }

    /**
     * check that the elements in the list are equal
     */
    @Test
    void testGetList() {
        for (int i = 0; i < experience.size(); i++) {
            assertEquals(experience.get(i).getID() + experience.get(i).getTitle(),
                ec.getExperienceList().get(i).getID() + ec.getExperienceList().get(i).getTitle());
        }
    }

    /**
     *
     */
    @Test
    void testAddExperience() {
        String id = ec.addExperience();
        assertNotNull(ec.getExperienceByID(id));
        assertEquals(2, ec.getExperienceList().size());
    }

    /**
     *
     */
    @Test
    void testRemoveExperience() {
        String id = ec.addExperience();
        assertNotNull(ec.getExperienceByID(id));
        ec.removeExperience(id);
        assertNull(ec.getExperienceByID(id));

    }

    /**
     * Verifying that the copy experience fields are equal to the original.
     * Note: the id is regenerated.
     */
    @Test
    void testAddExperienceCopy() {
        Experience exp = experience.get(0);
        exp.setDate("Date");
        exp.setLocation("Location");
        exp.setOrganization("Organization");
        exp.setTitle("Title");

        String id = ec.addExperience(exp);
        Experience copy = ec.getExperienceByID(id);
        assertEquals(copy.getDate(), exp.getDate());
        assertEquals(copy.getLocation(), exp.getLocation());
        assertEquals(copy.getOrganization(), exp.getOrganization());
        assertEquals(copy.getTitle(), exp.getTitle());

    }

}
