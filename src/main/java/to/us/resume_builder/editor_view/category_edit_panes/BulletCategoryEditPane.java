package to.us.resume_builder.editor_view.category_edit_panes;

import to.us.resume_builder.editor_view.components.BulletComponent;
import to.us.resume_builder.resume_components.category.BulletCategory;

import java.awt.*;

/**
 * Facilitates editing of the BulletCategory resume component.
 *
 * @author Ashley Lu Couch
 */
public class BulletCategoryEditPane extends CategoryEditPane {
    /**
     * The Bullet Component held by the Bullet Category to be displayed in the UI
     */
    private BulletComponent bulletComponent;
    /**
     * Creates a Bullet Category edit pane displaying the bullet component
     * @param category the Bullet Category to display in the JPanel
     */
    public BulletCategoryEditPane(BulletCategory category) {
        this.setLayout(new BorderLayout());
        bulletComponent = new BulletComponent(category);

        add(bulletComponent, BorderLayout.CENTER);
    }

    /**
     * Saves the information currently in the Bullet Component in the UI
     * to the Bullet Category
     */
    @Override
    public void save() {
        bulletComponent.save();
    }

    /**
     * Determines if the current Bullet Category has been modified
     * @return boolean indicating whether the Bullet Category was edited
     */
    public boolean isModified() {
        return bulletComponent.isModified();
    }
}