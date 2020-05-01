package to.us.resume_builder.presentation.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import to.us.resume_builder.business.controllers.EditorController;
import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.category.CategoryType;
import to.us.resume_builder.presentation.EditorCategorySelector;
import to.us.resume_builder.presentation.EditorStage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        es = new EditorStage(r.getCategoryByID(r.createCategory(CategoryType.BULLETS)));
        ecs = new EditorCategorySelector(r);
        ec = ec.create(es, ecs, r);
        System.out.println("Init called");
    }

    /**
     * Create the EditorController with null inputs to make
     * sure an exception is thrown
     */
    @Test
    public void testCreate2() {
        assertThrows(IllegalArgumentException.class, () -> { ec.create(null, null, null);});
    }

    /**
     * Add a Category and make sure the size has increased by 1
     */
    @Test
    public void testAddCategory() {
        ec.addCategory(CategoryType.BULLETS);
        assertEquals(2, r.getCategoryList().size());
    }
}
