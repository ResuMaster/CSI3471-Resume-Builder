package to.us.resume_builder.main_window;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import to.us.resume_builder.editor_view.category_edit_panes.CategoryEditPane;
import to.us.resume_builder.resume_components.category.Category;

/**
 * Header for all Category editors (in the EditStage). Gives user interface for
 * editing name, display name, and visibility of a category, as well as deletion
 * of the category.
 * 
 * @author Micah Schiewe
 * @author Jacob Curtis
 */
public class EditorCategoryHeader extends CategoryEditPane {

    private static final int NUM_COLS = 20;

    private Category category;
    private JTextField name, displayName;
    private JCheckBox toggled;
    private JButton delete;

    public EditorCategoryHeader(Category startingCategory, DeleteCategory deleteHandle) {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel left, right;

        // Create GUI
        left = createLabelPanel();
        right = createActionPanel();

        // Connect GUI to actions and data
        delete.addActionListener(e -> deleteHandle.delete());
        updateHeader(startingCategory);

        // Assemble GUI
        add(left);
        add(right);
        revalidate();
    }

    /**
     * Updates this header with the data in the new category to render.
     * 
     * @param newCategory The new category to display.
     */
    public void updateHeader(Category newCategory) {
        category = newCategory;

        // Update labels
        displayName.setText(newCategory.getDisplayName());
        name.setText(newCategory.getName());

        // Update box
        toggled.setSelected(newCategory.getVisible());

        // Repaint
        revalidate();
        repaint();
    }

    @Override
    public void save() {
        // Save display name
        if (!displayName.getText().contentEquals(""))
            category.setDisplayName(displayName.getText());

        // Save name
        if (!name.getText().contentEquals(""))
            category.setName(name.getText());

        // Save visible
        category.setVisible(toggled.getModel().isSelected());
    }

    @Override
    public boolean isModified() {
        return toggled.getModel().isSelected() != category.getVisible()
                || !displayName.getText().contentEquals(category.getDisplayName())
                || !name.getText().contentEquals(category.getName());
    }

    /**
     * Creates the labels for the GUI (the left side)
     * 
     * @return The newly-constructed GUI part
     */
    private JPanel createLabelPanel() {
        // Set up return panel
        JPanel left = new JPanel();

        // Create display name field
        displayName = new JTextField(NUM_COLS);
        displayName.setEditable(true);

        // Create name field
        name = new JTextField(NUM_COLS);
        name.setEditable(true);

        // Finalize and return component
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(displayName);
        left.add(name);
        return left;
    }

    /**
     * Creates the input boxes for the GUI (the right side)
     * 
     * @return The newly-constructed GUI part
     */
    private JPanel createActionPanel() {
        JPanel right = new JPanel();

        // Create toggle box
        toggled = new JCheckBox();

        // Create delete button
        delete = new JButton("X");
        delete.setBackground(Color.RED);

        // Finalize and return component
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.add(toggled);
        right.add(delete);
        return right;
    }

    public interface DeleteCategory {
        void delete();
    }
}
