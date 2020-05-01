package to.us.resume_builder.presentation.category_edit_panes;

import to.us.resume_builder.data.resume_components.Bullet;
import to.us.resume_builder.presentation.components.BulletListEditor;
import to.us.resume_builder.data.resume_components.category.BulletCategory;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Facilitates editing of the BulletCategory resume component.
 *
 * @author Ashley Lu Couch
 */
public class BulletCategoryEditPane extends CategoryEditPane {
    /**
     * Logs the saving of a BulletCategoryEditPane
     */
    private static final Logger LOG = Logger.getLogger(BulletCategoryEditPane.class.getName());

    /**
     * Facilitates editing and saving {@link Bullet}
     */
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
        LOG.logp(Level.INFO, BulletCategoryEditPane.class.getName(), "save", "saving the BulletCategory changes");
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