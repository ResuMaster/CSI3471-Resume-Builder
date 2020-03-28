package to.us.resume_builder.main_window;

import java.awt.*;

import javax.swing.*;

import to.us.resume_builder.resume_components.category.Category;

/**
 * Header for all Category editors (in the EditStage). Gives user interface for
 * generic {@link Category} edits (editing name, display name, and visibility of
 * a category, as well as deletion of the category).
 *
 * @author Micah Schiewe
 * @author Jacob Curtis
 */
public class EditorCategoryHeader extends JPanel {

    private static final int NUM_COLS = 20;

    /**
     * The category edited by this header
     */
    private Category category;

    /**
     * The fields containing and allowing edits to the name and display name
     */
    private JTextField name, displayName;

    /**
     * The component containing and allowing edits to category's visibility
     */
    private JCheckBox toggled;

    /**
     * The component enabling deletion of the category
     */
    private JButton delete;

    /**
     * Creates a new EditorCategoryHeader, prepared to perform generic {@link
     * Category} edits on startingCategory.
     *
     * @param startingCategory The category the editor defaults to editing. This
     *                         cannot be null.
     * @param deleteHandle     The method to call to delete the category being
     *                         edited. This should be a lambda passed from the
     *                         creating function which removes the category, and
     *                         also facilitates a callback to load a new
     *                         category to display.
     */
    public EditorCategoryHeader(Category startingCategory, DeleteCategory deleteHandle) {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel left, right;

        // Create GUI
        left = createLabelPanel();
        right = createActionPanel();

        // Connect GUI to actions and data
        delete.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you would like to delete this category?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                deleteHandle.delete();
            }
        });
        updateHeader(startingCategory);

        // Assemble GUI
        add(left);
        add(right);
        revalidate();
    }

    /**
     * Updates this header with the data in the new {@link Category} to render.
     *
     * @param newCategory The new category to display and edit.
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
     * Saves the currently-edited data to the resume in RAM.
     */
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

    /**
     * Determines whether the user has made any basic {@link Category} edits.
     *
     * @return A boolean telling if the Editor has been modified
     */
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
        JPanel left = new JPanel(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.HORIZONTAL;

        JLabel displayNameLabel = new JLabel("Section Title ", SwingConstants.RIGHT);
        JLabel nickNameLabel = new JLabel("Nickname (not visible) ", SwingConstants.RIGHT);
        displayNameLabel.setLabelFor(displayName);
        nickNameLabel.setLabelFor(name);

        // Create display name field
        displayName = new JTextField(NUM_COLS);
        displayName.setEditable(true);

        // Create name field
        name = new JTextField(NUM_COLS);
        name.setEditable(true);

        grid.gridx = 0;
        grid.gridy = 0;
        left.add(displayNameLabel, grid);

        grid.gridy = 1;
        left.add(nickNameLabel, grid);

        grid.gridwidth = 2;
        grid.gridx = 1;
        grid.gridy = 0;
        left.add(displayName, grid);
        grid.gridy = 1;
        left.add(name, grid);

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
        JPanel visibilityPanel = new JPanel();
        visibilityPanel.setLayout(new BoxLayout(visibilityPanel, BoxLayout.LINE_AXIS));
        JLabel VisibleLabel = new JLabel("Visibility ", SwingConstants.LEFT);
        VisibleLabel.setLabelFor(toggled);
        toggled = new JCheckBox();
        visibilityPanel.add(VisibleLabel);
        visibilityPanel.add(toggled);

        // Create delete button
        Box deleteWrapper = Box.createHorizontalBox();
        delete = new JButton("Delete Section");
        delete.setBackground(Color.RED);
        deleteWrapper.add(delete);
        deleteWrapper.add(Box.createHorizontalGlue());

        // Finalize and return component
        right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));
        right.add(visibilityPanel);
        right.add(deleteWrapper);
        return right;
    }

    /**
     * A handle used by the EditorCategoryHeader to report that the user has
     * decided to delete the currently-selected {@link Category}. This is the
     * only action the {@link EditorCategoryHeader} takes on this report,
     * meaning that the delete() method must handle confirming and removing the
     * category, as well as re-populating the {@link EditorCategoryHeader} with
     * a new {@link Category}.
     *
     * @author Micah
     */
    @FunctionalInterface
    public interface DeleteCategory {
        void delete();
    }
}
