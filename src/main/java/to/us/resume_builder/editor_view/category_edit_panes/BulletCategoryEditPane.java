package to.us.resume_builder.editor_view.category_edit_panes;

import to.us.resume_builder.editor_view.components.BulletComponent;
import to.us.resume_builder.resume_components.category.BulletCategory;

import java.awt.*;

public class BulletCategoryEditPane extends CategoryEditPane {
    private BulletComponent bulletComponent;
    /**
     * Creates a Bullet Category pane to see
     * @param category the category to display in the JPanel
     */
    public BulletCategoryEditPane(BulletCategory category) {
        this.setLayout(new BorderLayout());
        bulletComponent = new BulletComponent(category.getBulletList());

        add(bulletComponent, BorderLayout.CENTER);
    }

    @Override
    public void save() {
        bulletComponent.save();
    }

    /**
     * Determines if the current Category has been modified
     * @return boolean indicating whether the Category was edited
     */
    public boolean isModified() {
        return bulletComponent.isModified();
    }
}