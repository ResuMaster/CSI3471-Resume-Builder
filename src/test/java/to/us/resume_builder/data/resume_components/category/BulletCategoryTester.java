package to.us.resume_builder.data.resume_components.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import to.us.resume_builder.business.export_LaTeX.ResumeTemplate;
import to.us.resume_builder.data.resume_components.Bullet;
import to.us.resume_builder.data.resume_components.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for the {@link BulletCategory}
 * @author Brooklynn Stone
 */
public class BulletCategoryTester extends CategoryTester {
    private static Resume r;
    private static BulletCategory bc;
    private static List<Bullet> bullets;

    /**
     * Initializes the resume and 10 bullets with text "Bullet #."
     */
    @BeforeEach
    public void init() {
        r = new Resume();
        bc = new BulletCategory(r.createCategory(CategoryType.BULLETS));
        bullets = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            bullets.add(new Bullet(bc.addBullet()));
            bullets.get(i).setText("Bullet " + i + ".");
            bc.getBulletByID(bullets.get(i).getID()).setText("Bullet " + i + ".");
        }
        bc.setDisplayName("Display name");
        bc.setName("name");
    }

    /**
     * Verifies that getType returns a Bullet CategoryType
     */
    @Override
    @Test
    void testGetType() {
        assertEquals(CategoryType.BULLETS, bc.getType());
    }

    /**
     * Testing get and set display name
     */
    @Override
    @Test
    void testDisplayName() {
        assertEquals("Display name", bc.getDisplayName());
    }

    /**
     * Testing get and set name
     */
    @Override
    @Test
    void testName() {
        assertEquals("name", bc.getName());
    }

    /**
     * Verify that the getter returns all 10 bullets with the expected text and identifications
     */
    @Test
    public void testGetBulletList() {
        for(int i = 0; i < bullets.size(); i++) {
            assertEquals(bullets.get(i).getID()+bullets.get(i).getText(),
                    bc.getBulletList().get(i).getID()+bc.getBulletList().get(i).getText());
        }
    }

    /**
     * Verify that setting the column changes the column internally
     */
    @Test
    public void testSetColumn() {
        bc.setColumn(2);
        assertEquals(2, bc.getColumn());
    }

    /**
     * Verify that getting the column number returns 1 for an initialized {@link Bullet}
     */
    @Test
    public void testGetColumn() {
        assertEquals(1, bc.getColumn());
    }

    /**
     * Check that the {@link Bullet} returned from this method returns a {@link Bullet} with the same
     * text and identification
     */
    @Test
    public void testGetBulletByID() {
        assertEquals(bullets.get(2).getText()+bullets.get(2).getID(),
                bc.getBulletByID(bullets.get(2).getID()).getText() +
                        bc.getBulletByID(bullets.get(2).getID()).getID());
    }

    /**
     * Add a new {@link Bullet} to the {@link BulletCategory} and verify that the {@link BulletCategory}
     * contains the new {@link Bullet} and that the size of the bullet list is one more.
     */
    @Test
    public void testAddBullet() {
        String id = bc.addBullet();
        assertTrue(bc.checkBulletListID(id));
        assertEquals(11, bc.getBulletList().size());
    }

    /**
     * Add a {@link Bullet} which has already been initialized to the {@link BulletCategory}
     * and verify that the size is correct and that the {@link Bullet} was added to the end.
     */
    @Test
    public void testAddBullet2() {
        Bullet b = new Bullet("12345");
        String id = bc.addBullet(b);
        b = new Bullet(id);
        assertTrue(bc.checkBulletListID(id));
        assertEquals(11, bc.getBulletList().size());
        Bullet temp = bc.getBulletList().get(bc.getBulletList().size()-1);
        assertTrue((temp.getID()+temp.getText()+temp.getVisible()).
                equals(b.getID()+b.getText()+b.getVisible()));
    }

    /**
     * Remove a {@link Bullet} from the {@link BulletCategory} and verify that it cannot be
     * retrieved from the Bullet list which is one smaller than it was before.
     */
    @Test
    public void testRemoveBullet() {
        bc.removeBullet(bullets.get(0).getID());
        assertFalse(bc.checkBulletListID(bullets.get(0).getID()));
        assertEquals(9, bc.getBulletList().size());
    }

    /**
     * Verify that an expected {@link Bullet} returns true
     */
    @Test
    public void testCheckBulletListID() {
        Bullet temp = bullets.get(0);
        assertTrue(bc.checkBulletListID(temp.getID()));
    }

    /**
     * Verify that an unexpected {@link Bullet} returns false
     */
    @Test
    public void testCheckBulletListID2() {
        Bullet temp = new Bullet("1234");
        assertFalse(bc.checkBulletListID(temp.getID()));
    }

    /**
     * Verify the correct formatting of a LaTeX String with all 10 {@link Bullet}s
     */
    @Test
    public void testFormatLaTeXString() {
        String s = "\\begin{resumebulletcategory}{}\n    ";
        for(int i = 0; i < 10; i++) {
            s += "\\item Bullet " + i + ".\n";
        }
        s += "\n\\end{resumebulletcategory}\n\n";
        assertEquals(s, bc.formatLaTeXString(ResumeTemplate.DEFAULT));
    }
}
