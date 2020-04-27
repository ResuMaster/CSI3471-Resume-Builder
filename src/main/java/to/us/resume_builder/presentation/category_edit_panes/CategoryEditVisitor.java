package to.us.resume_builder.presentation.category_edit_panes;

import to.us.resume_builder.data.resume_components.CategoryVisitor;
import to.us.resume_builder.data.resume_components.category.BulletCategory;
import to.us.resume_builder.data.resume_components.category.ExperienceCategory;
import to.us.resume_builder.data.resume_components.category.HeaderCategory;
import to.us.resume_builder.data.resume_components.category.TextCategory;

/**
 * Allows dynamic creation and retrieval of a CategoryEditPane
 * 
 * @author Micah
 */
public class CategoryEditVisitor implements CategoryVisitor {

    /** Storage for the new editor */
    private CategoryEditPane editor = null;

    /**
     * Allows access to the created editor
     * 
     * @return The editor created while visiting the Category.
     */
    public CategoryEditPane getEditor() {
        return editor;
    }

    /**
     * Resets the editor to avoid having an old editor/dangling reference hanging
     * around.
     */
    public void resetEditor() {
        editor = null;
    }

    @Override
    public void visit(BulletCategory c) {
        editor = new BulletCategoryEditPane(c);
    }

    @Override
    public void visit(TextCategory c) {
        editor = new TextCategoryEditPane(c);
    }

    @Override
    public void visit(ExperienceCategory c) {
        editor = new ExperienceCategoryEditPane(c);
    }

    @Override
    public void visit(HeaderCategory c) {
        editor = new HeaderCategoryEditPane(c);
    }
}
