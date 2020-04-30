package to.us.resume_builder.data.resume_components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import to.us.resume_builder.data.resume_components.category.BulletCategory;
import to.us.resume_builder.data.resume_components.category.CategoryType;
import to.us.resume_builder.data.resume_components.category.ExperienceCategory;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class for the {@link Experience}
 *
 * @author Ashley Lu Couch
 */
public class ExperienceTester {

    private static Resume r = null;
    private static ExperienceCategory ec = null;
    private static Experience experience = null;
    private static List<Bullet> bullets = null;


    /**
     * Create a {@link Resume} and {@link ExperienceCategory} with an {@link
     * Experience} and a {@link Bullet} on that Experience.
     */
    @BeforeEach
    public void init() {
        r = new Resume();
        ec = new ExperienceCategory(r.createCategory(CategoryType.EXPERIENCE));
        experience = new Experience(ec.addExperience());
        experience.addBullet();
        bullets = new LinkedList<>(experience.getBulletList());
    }

    /**
     * Verify that setting the Date and getting it assert they are equal
     */
    @Test
    public void testDate() {
        // testing default value
        assertEquals("", experience.getDate());
        experience.setDate("Date");
        assertEquals("Date", experience.getDate());
    }

    /**
     * Verify that setting the Organization and getting it assert they are equal
     */
    @Test
    public void testOrganization() {
        // testing default value
        assertEquals("", experience.getOrganization());
        experience.setOrganization("Organization");
        assertEquals("Organization", experience.getOrganization());
    }

    /**
     * Verify that setting the Location and getting it assert they are equal
     */
    @Test
    public void testLocation() {
        // testing default value
        assertEquals("", experience.getLocation());
        experience.setLocation("Location");
        assertEquals("Location", experience.getLocation());
    }

    /**
     * Verify that setting the Title and getting it assert they are equal
     */
    @Test
    public void testTitle() {
        // testing default value
        assertEquals("", experience.getTitle());
        experience.setTitle("Title");
        assertEquals("Title", experience.getTitle());
    }

    /**
     * Verify that setting the column changes the column internally
     */
    @Test
    public void testSetColumn() {
        experience.setColumn(2);
        assertEquals(2, experience.getColumn());
    }

    /**
     * Verify that getting the column number returns 1 for an initialized {@link
     * Bullet}
     */
    @Test
    public void testGetColumn() {
        assertEquals(1, experience.getColumn());
    }

    /**
     * Check that the {@link Bullet} returned from this method returns a {@link
     * Bullet} with the same text and identification
     */
    @Test
    public void testGetBulletByID() {
        assertEquals(bullets.get(0).getText() + bullets.get(0).getID(),
            experience.getBulletByID(bullets.get(0).getID()).getText() +
                experience.getBulletByID(bullets.get(0).getID()).getID());
    }

    /**
     * Add a new {@link Bullet} to the {@link BulletCategory} and verify that
     * the {@link BulletCategory} contains the new {@link Bullet} and that the
     * size of the bullet list is one more.
     */
    @Test
    public void testAddBullet() {
        String id = experience.addBullet();
        assertTrue(experience.checkBulletListID(id));
        assertEquals(2, experience.getBulletList().size());
    }

    /**
     * Remove a {@link Bullet} from the {@link BulletCategory} and verify that
     * it cannot be retrieved from the Bullet list which is one smaller than it
     * was before.
     */
    @Test
    public void testRemoveBullet() {
        experience.removeBullet(bullets.get(0).getID());
        assertNull(experience.getBulletByID(bullets.get(0).getID()));
        assertEquals(0, experience.getBulletList().size());
    }

}
