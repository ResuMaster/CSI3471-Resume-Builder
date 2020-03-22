package to.us.resume_builder.editor_view.category_edit_panes;

import to.us.resume_builder.editor_view.components.BulletListEditor;
import to.us.resume_builder.resume_components.category.BulletCategory;

import java.awt.*;

/**
 *
 * @author Ashley Lu Couch
 */
public class BulletCategoryEditPane extends CategoryEditPane {
    private BulletListEditor bulletListEditor;
    /**
     * Creates a Bullet Category pane to see
     * @param category the category to display in the JPanel
     */
    public BulletCategoryEditPane(BulletCategory category) {
        this.setLayout(new BorderLayout());
        bulletListEditor = new BulletListEditor(category);

        add(bulletListEditor, BorderLayout.CENTER);
    }

    @Override
    public void save() {
        bulletListEditor.save();
    }

    /**
     * Determines if the current Category has been modified
     * @return boolean indicating whether the Category was edited
     */
    public boolean isModified() {
        return bulletListEditor.isModified();
    }
}