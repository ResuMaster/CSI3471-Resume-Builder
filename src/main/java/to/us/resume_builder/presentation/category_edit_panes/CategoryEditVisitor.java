package to.us.resume_builder.presentation.category_edit_panes;

import to.us.resume_builder.data.resume_components.CategoryVisitor;
import to.us.resume_builder.data.resume_components.category.BulletCategory;
import to.us.resume_builder.data.resume_components.category.ExperienceCategory;
import to.us.resume_builder.data.resume_components.category.HeaderCategory;
import to.us.resume_builder.data.resume_components.category.TextCategory;

/**
 * Allows dynamic creation and retrieval of a CategoryEditPane
 * <p>
 * Implementation of Design Pattern: Visitor
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

    /**
     * Creates a {@link BulletCategoryEditPane} when given a {@link BulletCategory}
     * @param c The {@link BulletCategory} to add to the {@link BulletCategoryEditPane}.
     */
    @Override
    public void visit(BulletCategory c) {
        editor = new BulletCategoryEditPane(c);
    }

    /**
     * Creates a {@link TextCategoryEditPane} when given a {@link TextCategory}
     * @param c The {@link TextCategory} to add to the {@link TextCategoryEditPane}.
     */
    @Override
    public void visit(TextCategory c) {
        editor = new TextCategoryEditPane(c);
    }

    /**
     * Creates a {@link ExperienceCategoryEditPane} when given a {@link ExperienceCategory}
     * @param c The {@link ExperienceCategory} to add to the {@link ExperienceCategoryEditPane}.
     */
    @Override
    public void visit(ExperienceCategory c) {
        editor = new ExperienceCategoryEditPane(c);
    }

    /**
     * Creates a {@link HeaderCategoryEditPane} when given a {@link HeaderCategory}
     * @param c The {@link HeaderCategory} to add to the {@link HeaderCategoryEditPane}.
     */
    @Override
    public void visit(HeaderCategory c) {
        editor = new HeaderCategoryEditPane(c);
    }
}
