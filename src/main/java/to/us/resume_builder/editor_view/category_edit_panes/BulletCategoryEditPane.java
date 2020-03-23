package to.us.resume_builder.editor_view.category_edit_panes;

import to.us.resume_builder.editor_view.components.BulletListEditor;
import to.us.resume_builder.resume_components.category.BulletCategory;

import java.awt.*;

/**
 * Facilitates editing of the BulletCategory resume component.
 *
 * @author Ashley Lu Couch
 */
public class BulletCategoryEditPane extends CategoryEditPane {
    private BulletListEditor bulletListEditor;

    /**
     * Creates a Bullet Category edit pane displaying the bullet component
     * @param category the Bullet Category to display in the JPanel
     */
    public BulletCategoryEditPane(BulletCategory category) {
        this.setLayout(new BorderLayout());
        bulletListEditor = new BulletListEditor(category);

        add(bulletListEditor, BorderLayout.CENTER);
    }

    /**
     * Saves the information currently in the Bullet Component in the UI
     * to the Bullet Category
     */
    @Override
    public void save() {
        bulletListEditor.save();
    }

    /**
     * Determines if the current Bullet Category has been modified
     * @return boolean indicating whether the Bullet Category was edited
     */
    public boolean isModified() {
        return bulletListEditor.isModified();
    }
}