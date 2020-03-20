package to.us.resume_builder.main_window;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import to.us.resume_builder.resume_components.category.Category;

/**
 * @author Micah
 * @author Jacob
 */
public class EditorCategoryHeader extends JPanel {

    private Category category;
    private JTextField name, displayName;
    private JCheckBox toggled;
    private JButton delete;

    public EditorCategoryHeader(Category startingCategory, DeleteCategory deleteHandle) {
        super(new FlowLayout(FlowLayout.CENTER));

        JPanel left, right;

        // Create main container
        category = startingCategory;

        // Create GUI
        left = createLabelPanel();
        right = createActionPanel();

        // Connect GUI to actions
        toggled.addItemListener(e -> toggleCategory(e.getStateChange() == ItemEvent.SELECTED));
        delete.addActionListener(e -> deleteHandle.delete());

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

    /**
     * Toggles the visibility of the underlying {@link Category}.
     * 
     * @param toggleOn Whether or not the category is visible.
     */
    private void toggleCategory(boolean toggleOn) {
        category.setVisible(toggleOn);
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
        displayName = new JTextField(category.getDisplayName());
        displayName.setEditable(false);

        // Create name field
        name = new JTextField(category.getName());
        name.setEditable(false);

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
        toggled.setSelected(category.getVisible());

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
