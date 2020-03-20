package to.us.resume_builder.main_window;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import to.us.resume_builder.editor_view.category_edit_panes.BulletCategoryEditPane;
import to.us.resume_builder.editor_view.category_edit_panes.CategoryEditPane;
import to.us.resume_builder.editor_view.category_edit_panes.ExperienceCategoryEditPane;
import to.us.resume_builder.editor_view.category_edit_panes.HeaderCategoryEditPane;
import to.us.resume_builder.editor_view.category_edit_panes.TextCategoryEditPane;
import to.us.resume_builder.resume_components.category.BulletCategory;
import to.us.resume_builder.resume_components.category.Category;
import to.us.resume_builder.resume_components.category.ExperienceCategory;
import to.us.resume_builder.resume_components.category.HeaderCategory;
import to.us.resume_builder.resume_components.category.TextCategory;

/**
 * Class to tie together the various Category editors into a standard interface.
 * Allows saving to the RAM Resume.
 * 
 * @author Jacob Curtis
 * @author Micah Schiewe
 */
public class EditorStage extends JPanel {
    /**
     * The container for the currently-selected CategoryEditPane; needed to change
     * category editors.
     */
    private JPanel editContainer;

    /**
     * The editor for the currently-selected Category; needed to swap category
     * editors.
     */
    private CategoryEditPane edit;

    /** Editor for the name, display name, and visibility of the current Category */
    private EditorCategoryHeader header;

    /**
     * Controller for the editor. Synchronizes actions across the different
     * graphical components.
     */
    private EditorController controller = null;

    /** The category currently loaded to edit. */
    private Category category;

    /**
     * Constructs a EditorStage ready to edit the provided category.
     * 
     * @param startingCategory The category to initially edit. CANNOT BE NULL.
     */
    public EditorStage(Category startingCategory) {
        JButton saveButton;
        JScrollPane scroller;

        // Create central UI
        editContainer = new JPanel();
        editContainer.setLayout(new BoxLayout(editContainer, BoxLayout.Y_AXIS));

        category = startingCategory;
        header = new EditorCategoryHeader(startingCategory, () -> controller.removeCategory(category.getID()));
        edit = getEditor(startingCategory);
        editContainer.add(header);
        editContainer.add(edit);
        scroller = new JScrollPane(editContainer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Save button
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> save());
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Assemble.
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(scroller);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(saveButton);
    }

    /**
     * Displays the given category to edit. If there are unsaved changes to the
     * category
     * 
     * @param toEdit The category to replace the current category with
     */
    public void showInEditor(Category toEdit) {
        // Remove the old category
        clearCurrentEditor();

        // Register the new category
        category = toEdit;
        header.updateHeader(toEdit);
        edit = getEditor(toEdit);
        editContainer.add(edit);

        // Alert Swing that the component hierarchy has changed
        revalidate();
    }

    /**
     * Registers the EditorStage with a controller.
     * 
     * @param controller The controller this EditorStage is to direct actions to.
     */
    public void setController(EditorController controller) {
        this.controller = controller;
    }

    private void clearCurrentEditor() {
        // If the editor is null, then there is nothing to clear
        if (edit == null)
            return;

        // If there are unsaved changes, prompt for and save them.
        if (edit.isModified() && JOptionPane.showConfirmDialog(this,
                "You have changes not stored to this session's Resume.\n\nDo you want to stash these changes?") == JOptionPane.YES_OPTION) {
            save();
        }

        // Dispose of the now-unneeded editor
        editContainer.remove(edit);
        edit = null;

    }

    /**
     * Saves all changes in the editor to the resume in RAM.
     */
    private void save() {
        edit.save();
        header.save();
    }

    /**
     * Generates the proper editor component for the provided category.
     * 
     * @param toEdit The category to get an editor for
     * @return A GUI editor for the provided {@link Category}
     */
    private CategoryEditPane getEditor(Category toEdit) {
        CategoryEditPane editPane = null;
        // This switch controls creating different editors for each CategoryType
        // TODO replace with a visitor pattern.
        switch (toEdit.getType()) {
        case BULLETS:
            editPane = new BulletCategoryEditPane((BulletCategory) toEdit);
            break;
        case EXPERIENCE:
            editPane = new ExperienceCategoryEditPane((ExperienceCategory) toEdit);
            break;
        case HEADER:
            editPane = new HeaderCategoryEditPane((HeaderCategory) toEdit);
            break;
        case TEXT:
            editPane = new TextCategoryEditPane((TextCategory) toEdit);
            break;
        default:
            break;
        }

        return editPane;
    }
}
