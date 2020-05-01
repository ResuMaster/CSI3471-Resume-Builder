package to.us.resume_builder.business.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.category.CategoryType;
import to.us.resume_builder.presentation.EditorCategorySelector;
import to.us.resume_builder.presentation.EditorStage;
import to.us.resume_builder.presentation.controllers.EditorController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests methods in the {@link EditorController} class
 *
 * @author Brooklynn Stone
 */
public class EditorControllerTester extends EditorController {
    /**
     * EditorController to perform testss on
     */
    EditorController ec;

    /**
     * Resume to add Categories to and test with
     */
    Resume r;

    /**
     * Used to verify adding and removing Categories with EditorController
     */
    EditorStage es;

    /**
     * Used to verify which Category is in focus
     */
    EditorCategorySelector ecs;

    /**
     * Initialize Resume and EditorController
     */
    @BeforeEach
    public void init() {
        ec = new EditorController();
        r = new Resume();
        ec.loadResume(r);
        r.createCategory(CategoryType.BULLETS);
        es = new EditorStage(ec.resume.getCategoryList().get(0));
        ecs = new EditorCategorySelector(ec.resume);
        ec = ec.create(es, ecs, r);
        System.out.println("Init called");
    }

    /**
     * Create the EditorController with null inputs to make
     * sure an exception is thrown
     */
    @Test
    public void testCreate2() {
        try {
            ec.create(null, null, null);
            assert(false);
        }
        catch (IllegalArgumentException e) {
            assert(true);
        }
    }

    /**
     * Add a Category to the Resume and then load it. Validate
     * that the stage has loaded the designated Category
     */
    @Test
    public void testLoadCategory() {
        String id = ec.resume.createCategory(CategoryType.BULLETS);
        ec.loadCategory(id);
        assertTrue(ec.stage.getEditID().equals(id));
    }

    /**
     * Add a Category and make sure the size has increased by 1
     */
    @Test
    public void testAddCategory() {
        ec.addCategory(CategoryType.BULLETS);
        assertEquals(2, ec.resume.getCategoryList().size());
    }

    /**
     * Add then remove a Category and make sure the size decreases by 1
     */
    @Test
    public void testRemoveCategory() {
        ec.addCategory(CategoryType.BULLETS);
        String id = ec.resume.getCategoryList().get(0).getID();
        ec.removeCategory(id);
        assertEquals(1, ec.sideList.getModel().getSize());
    }
}
