package to.us.resume_builder.presentation;

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import to.us.resume_builder.data.resume_components.category.Category;
import to.us.resume_builder.presentation.category_edit_panes.CategoryEditPane;
import to.us.resume_builder.presentation.category_edit_panes.CategoryEditVisitor;
import to.us.resume_builder.presentation.controllers.EditorController;

/**
 * Class to tie together the various Category editors into a standard interface.
 * Allows saving to the resume in RAM.
 * 
 * @author Jacob Curtis
 * @author Micah Schiewe
 */
public class EditorStage extends JPanel {
    /**
     * Logging {@link Category} movement into the stage
     */
    private static final Logger LOG = Logger.getLogger(EditorStage.class.getName());

    /**
     * String prompt when the user tries to change windows without saving
     */
    private static final String UNSAVED_PROMPT = "You have unsaved changes. Do you want to save?";

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
    private transient EditorController controller = null;

    /** The category currently loaded to edit. */
    private transient Category category;

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
        editContainer.setLayout(new BoxLayout(editContainer, BoxLayout.PAGE_AXIS));

        category = startingCategory;
        header = new EditorCategoryHeader(startingCategory, () -> controller.removeCategory(category.getID()));
        edit = getEditor(startingCategory);
        editContainer.add(header);
        editContainer.add(new JSeparator(JSeparator.HORIZONTAL));
        editContainer.add(edit);
        scroller = new JScrollPane(editContainer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Save button
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> save());
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Assemble.
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(scroller);
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
        if (!clearCurrentEditor()) {
            LOG.logp(Level.WARNING, EditorStage.class.getName(), "showInEditor", "Failed to clear current editor, abort category change");
            return false;
        }

        // Register the new category
        category = toEdit;
        header.updateHeader(toEdit);
        edit = getEditor(toEdit);
        editContainer.add(edit);

        // Alert Swing that the component hierarchy has changed
        revalidate();
        LOG.logp(Level.INFO, EditorStage.class.getName(), "showInEditor", "Category successfully changed in Editor");
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
        controller.repaintSideList();
    }

    /**
     * Generates the proper editor component for the provided category.
     * 
     * @param toEdit The category to get an editor for
     * @return A GUI editor for the provided {@link Category}
     */
    private CategoryEditPane getEditor(Category toEdit) {
        CategoryEditVisitor cev = new CategoryEditVisitor();
        CategoryEditPane toReturn = null;

        // Usage of the Visitor design pattern.
        toEdit.accept(cev);
        toReturn = cev.getEditor();
        cev.resetEditor();

        return toReturn;
    }
}
