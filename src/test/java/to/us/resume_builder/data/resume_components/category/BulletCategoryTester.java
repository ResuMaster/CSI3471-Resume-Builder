package to.us.resume_builder.data.resume_components.category;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import to.us.resume_builder.business.export_LaTeX.ResumeTemplate;
import to.us.resume_builder.data.resume_components.Bullet;
import to.us.resume_builder.data.resume_components.Resume;

import java.util.ArrayList;
import java.util.List;

public class BulletCategoryTester {
    private static Resume r;
    private static BulletCategory bc;
    private static List<Bullet> bullets;

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
    }

    @Test
    public void testGetBulletList() {
        for(int i = 0; i < bullets.size(); i++) {
            assertEquals(bullets.get(i).getID()+bullets.get(i).getText(),
                    bc.getBulletList().get(i).getID()+bc.getBulletList().get(i).getText());
        }
    }

    @Test
    public void testSetColumn() {
        bc.setColumn(2);
        assertEquals(2, bc.getColumn());
    }

    @Test
    public void testGetColumn() {
        assertEquals(1, bc.getColumn());
    }

    @Test
    public void testGetBulletByID() {
        assertEquals(bullets.get(2).getText()+bullets.get(2).getID(),
                bc.getBulletByID(bullets.get(2).getID()).getText() +
                        bc.getBulletByID(bullets.get(2).getID()).getID());
    }

    @Test
    public void testAddBullet() {
        String id = bc.addBullet();
        assertTrue(bc.checkBulletListID(id));
        assertEquals(11, bc.getBulletList().size());
    }

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

    @Test
    public void testRemoveBullet() {
        bc.removeBullet(bullets.get(0).getID());
        assertTrue(!bc.checkBulletListID(bullets.get(0).getID()));
        assertEquals(9, bc.getBulletList().size());
    }

    @Test
    public void testCheckBulletListID() {
        Bullet temp = bullets.get(0);
        assertTrue(bc.checkBulletListID(temp.getID()));
    }

    @Test
    public void testCheckBulletListID2() {
        Bullet temp = new Bullet("1234");
        assertFalse(bc.checkBulletListID(temp.getID()));
    }

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
