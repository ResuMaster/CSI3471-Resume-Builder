package to.us.resume_builder.data.resume_components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import to.us.resume_builder.data.resume_components.category.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link Resume}
 *
 * @author Ashley Lu Couch
 */
public class ResumeTester {

    private static Resume r = null;
    private static List<Category> categoryList = null;
    private static List<String> categoryIDs = null;

    /**
     * Initializes the resume and 4 categories with text "[TYPE] Category #."
     */
    @BeforeEach
    public void init() {
        r = new Resume();
        categoryList = new ArrayList<>();
        categoryIDs = new ArrayList<>();

        int index = 0;
        // create header category
        categoryList.add(new HeaderCategory(r.createCategory(CategoryType.HEADER)));
        categoryList.get(index).setName("header Category.");
        categoryIDs.add(categoryList.get(index).id);
        ++index;
        //create text category
        categoryList.add(new TextCategory(r.createCategory(CategoryType.TEXT)));
        categoryList.get(index).setName("text Category.");
        categoryIDs.add(categoryList.get(index).id);
        ++index;
        //create experience category
        categoryList.add(new ExperienceCategory(r.createCategory(CategoryType.EXPERIENCE)));
        categoryList.get(index).setName("experiences Category.");
        categoryIDs.add(categoryList.get(index).id);
        ++index;
        //create bullet category
        categoryList.add(new BulletCategory(r.createCategory(CategoryType.BULLETS)));
        categoryList.get(index).setName("bullets Category.");
        categoryIDs.add(categoryList.get(index).id);
        r.setCategoryList(categoryList);
    }

    /**
     * Verify that when a resume is created it is valid
     */
    @Test
    public void testCreateResume() {
        assertNotNull(r);
    }

    /**
     * Verify that when a category is created it is with a random id that is not
     * equal to any others
     */
    @Test
    public void testRandomID() {
        categoryList = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            //create text category
            categoryList.add(new TextCategory(r.createCategory(CategoryType.TEXT)));
            categoryList.get(i).setName("text Category " + i + ".");
        }
        for (Category c : categoryList) {
            for (Category o : categoryList) {
                if (c != o) {
                    assertNotEquals(c.id, o.id);
                }
            }
        }
    }

    /**
     * Verify that the category list contains all 4 categorie
     */
    @Test
    public void testCreateCategory() {
        assertEquals(4, categoryList.size());
    }

    /**
     * Verify that each category created is of the correct type
     */
    @Test
    public void testCategoryType() {
        int index = 0;
        //header category
        assertEquals(CategoryType.HEADER, categoryList.get(index++).getType());
        //text category
        assertEquals(CategoryType.TEXT, categoryList.get(index++).getType());
        //experience category
        assertEquals(CategoryType.EXPERIENCE, categoryList.get(index++).getType());
        //bullet category
        assertEquals(CategoryType.BULLETS, categoryList.get(index++).getType());
    }

    /**
     *
     */
    @Test
    public void testGetCategoryList() {
        assertEquals(categoryList.size(), r.getCategoryList().size());
        assertEquals(categoryList, r.getCategoryList());
    }

    /**
     * Verify that the list of category id's find a category in the resume
     */
    @Test
    public void testContains() {
        for (String id : categoryIDs){
            assertNotNull(r.getCategoryByID(id));
        }
        // testing with an id that could never be generated
        assertNull(r.getCategoryByID("1234"));
    }

    /**
     * Verify that the list of category id's find a category in the resume
     */
    @Test
    public void testRemoveAndContains() {
        for (String id : categoryIDs){
            assertNotNull(r.getCategoryByID(id));
            r.removeCategoryByID(id);
            assertNull(r.getCategoryByID(id));
        }
    }


}
