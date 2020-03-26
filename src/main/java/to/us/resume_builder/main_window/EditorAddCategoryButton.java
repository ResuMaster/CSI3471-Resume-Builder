package to.us.resume_builder.main_window;

import java.lang.ModuleLayer.Controller;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import to.us.resume_builder.resume_components.Resume;
import to.us.resume_builder.resume_components.category.CategoryType;

/**
 * The button which handles requesting a new {@link to.us.resume_builder.resume_components.category.Category
 * Category} be added to the underlying {@link Resume}.
 *
 * @author Micah
 */
public class EditorAddCategoryButton extends JButton {
    private static final String LABEL = "Add Section...";
    private static final String GET_TYPE_MESSAGE = "Select a section type:";

    /**
     * Controller which can add the category to the resume.
     */
    private EditorController controller;

    /**
     * Creates a new button ready to pass a request for a new category to its
     * {@link Controller}.
     */
    public EditorAddCategoryButton() {
        super(LABEL);
        addActionListener(e -> addCategory());
    }

    /**
     * Passes a request from the user for a new Category to the controller.
     */
    private void addCategory() {
        CategoryType type = getType();
        if (type != null)
            controller.addCategory(type);
    }

    /**
     * Gets the type of a desired new category from the user.
     *
     * @return The {@link CategoryType} corresponding to the desired new {@link
     *     Category}.
     */
    private CategoryType getType() {
        CategoryType[] values = CategoryType.values();
        return (CategoryType) JOptionPane.showInputDialog(this, GET_TYPE_MESSAGE, LABEL, JOptionPane.PLAIN_MESSAGE,
            null, values, values[0]);
    }

    /**
     * Registers the controller to alert of add category requests.
     *
     * @param e The controller to contact.
     */
    public void setController(EditorController e) {
        controller = e;
    }
}
