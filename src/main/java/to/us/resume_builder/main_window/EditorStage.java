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
 * Allows saving to the resume in RAM.
 * 
 * @author Jacob Curtis
 * @author Micah Schiewe
 */
public class EditorStage extends JPanel {
    private static final String UNSAVED_PROMPT = "You have changes not stored to this session's Resume.\n\nDo you want to stash these changes?";

    /**
     * The component parent of the currently-selected {@link CategoryEditPane};
     * needed to change category editors.
     */
    private JPanel editContainer;

    /**
     * The editor for the currently-selected {@link Category}; needed to change
     * category editors.
     */
    private CategoryEditPane edit;

    /** Editor for attributes common to all {@link Category Categories} */
    private EditorCategoryHeader header;

    /** Synchronizes actions across different components. */
    private EditorController controller = null;

    /** The category currently loaded to edit. */
    private Category category;

    /**
     * Constructs a EditorStage ready to edit the provided category.
     * 
     * @param startingCategory The category to initially edit. Cannot be null.
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
     * category, it prompts for the user to fix them. If the user cancels, then the
     * switch is aborted.
     * 
     * @param toEdit The category to replace the current category with.
     * 
     * @return Whether or not the switch was aborted.
     */
    public boolean showInEditor(Category toEdit) {
        // Remove the old category. If clear fails, abort changing categories
        if (!clearCurrentEditor())
            return false;

        // Register the new category
        category = toEdit;
        header.updateHeader(toEdit);
        edit = getEditor(toEdit);
        editContainer.add(edit);

        // Alert Swing that the component hierarchy has changed
        revalidate();
        return true;
    }

    /**
     * Registers the EditorStage with a controller.
     * 
     * @param controller The controller this EditorStage is to direct actions to.
     */
    public void setController(EditorController controller) {
        this.controller = controller;
    }

    /**
     * Gets the ID of the currently-selected category.
     * 
     * @return The ID of the category open in the editor.
     */
    public String getEditID() {
        return category.getID();
    }

    /**
     * Clears the editor. If there are changes which have not been stashed to the
     * resume in RAM, then it prompts the user for whether or not to save these
     * changes. The clear is aborted if the user cancels saving unsaved changes.
     * 
     * @return Whether or not the editor was cleared successfully.
     */
    private boolean clearCurrentEditor() {
        // If the editor is null, then there is nothing to clear, so the clear succeeded
        if (edit == null)
            return true;

        boolean returnFlag = true;

        // If there are unsaved changes, prompt for and save them.
        if (edit.isModified() || header.isModified()) {
            // Ask if the user wants to save their changes
            int result = JOptionPane.showConfirmDialog(this, UNSAVED_PROMPT);

            // Parse response
            switch (result) {
            case JOptionPane.YES_OPTION:
                save();
                break;
            case JOptionPane.CANCEL_OPTION:
                returnFlag = false;
                break;
            default:
                break;
            }
        }

        // Dispose of the now-unneeded editor, if the clear wasn't cancelled
        if (returnFlag) {
            editContainer.remove(edit);
            edit = null;
        }
        return returnFlag;
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
