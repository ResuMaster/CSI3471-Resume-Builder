package to.us.resume_builder.data.resume_components.category;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import to.us.resume_builder.data.resume_components.Bullet;
import to.us.resume_builder.data.resume_components.Resume;

import java.util.ArrayList;
import java.util.List;

public class BulletCategoryTester {
    private static Resume r;
    private static BulletCategory bc;
    private static List<Bullet> bullets;

    @BeforeAll
    public static void init() {
        r = new Resume();
        bc = new BulletCategory(r.createCategory(CategoryType.BULLETS));
        bullets = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            bullets.add(new Bullet(bc.addBullet()));
            bullets.get(i).setText("Bullet " + i + ".");
        }
    }

    @Test
    public void testGetBulletList() {
        assertEquals(bullets, bc.getBulletList());
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
        assertEquals(bullets.get(2).getID(), bc.getBulletByID(bullets.get(2).getID()));
    }

    @Test
    public void testAddBullet() {
        bullets.add(new Bullet(bc.addBullet()));
        assertEquals(11, bc.getBulletList().size());
    }

    @Test
    public void testRemoveBullet() {
        bc.removeBullet(bullets.get(0).getID());
        assertTrue(!bc.checkBulletListID(bullets.get(0).getID()));
        assertEquals(9, bc.getBulletList().size());
    }

    @Test
    public void testCheckBulletListID() {

    }

    @Test
    public void testFormatLaTeXString() {

    }
}
