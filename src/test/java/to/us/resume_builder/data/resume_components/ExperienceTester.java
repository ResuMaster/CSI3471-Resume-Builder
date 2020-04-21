package to.us.resume_builder.data.resume_components;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import java.lang.Object;

import to.us.resume_builder.business.export_LaTeX.ResumeTemplate;
import to.us.resume_builder.data.resume_components.category.BulletCategory;
import to.us.resume_builder.data.resume_components.category.CategoryType;

public class BulletTester {
    private static Resume r = null;
    private static BulletCategory bc = null;
    private static Bullet b = null;


    @BeforeEach
    public void init() {
        r = new Resume();
        bc = new BulletCategory(r.createCategory(CategoryType.BULLETS));
        b = new Bullet(bc.addBullet());
        b.setText("This is a bullet.");
    }

    @Test
    public void testGetText() {
        assertEquals("This is a bullet.", b.getText());
    }

    @Test
    public void testSetText() {
        b.setText("This is not a bullet.");
        assertEquals("This is not a bullet.", b.getText());
    }

    @Test
    public void testFormatLaTeXString() {
        assertEquals("\\item This is a bullet.\n", b.formatLaTeXString(ResumeTemplate.DEFAULT));
    }
}
