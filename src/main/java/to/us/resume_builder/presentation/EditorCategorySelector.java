package to.us.resume_builder.presentation;

import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import to.us.resume_builder.business.controllers.EditorController;
import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.ResumeComponent;
import to.us.resume_builder.data.resume_components.category.Category;

/**
 * Selector for the Editor Category List. Controlled by an EditorController.
 *
 * @author Jacob Curtis
 * @author Micah Schiewe
 */
public class EditorCategorySelector extends JPanel implements ListSelectionListener {
    /**
     * The categories to be selected from.
     */
    private JList<Category> categories;

    /**
     * Default implementation for the list selector model.
     */
    private DefaultListModel<Category> model;

    /**
     * Mapping from Category ID to Category.
     */
    private Map<String, Category> idToCategory;

    /**
     * The scroll pane for the list selector.
     */
    private JScrollPane scroll;

    /**
     * The controller which allows updating sibling editor components.
     */
    private EditorController controller = null;

    /**
     * Constructs an EditorCategorySelector given a Resume object.
     *
     * @param r The resume to construct the EditorCategorySelector from.
     */
    public EditorCategorySelector(Resume r) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Initialize model and categories
        model = new DefaultListModel<>();
        model.addAll(r.getCategoryList());
        categories = new JList<>(model);

        // Populate map of Category ID to Category
        idToCategory = r.getCategoryList().stream().collect(Collectors.toMap(ResumeComponent::getID, v -> v));

        scroll = new JScrollPane();

        // Listen to events, which are delegated to the EditorController.
        categories.addListSelectionListener(this);

        scroll.add(categories);
        scroll.getViewport().setView(categories);
        add(scroll);
    }

    /**
     * Allows you to remove a Category from the list by its ID. Assumes you
     * cannot remove the last Category.
     *
     * @param id The ID of the category to be removed.
     */
    public void removeCategory(String id) {
        // Return if no selection or only one category available
        if (categories.isSelectionEmpty() || model.getSize() == 1) {
            return;
        }

        // Get the Category for the iD
        Category c = idToCategory.get(id);
        if (c == null) {
            return;
        }

        // Update selected index since Category was deleted, and re-render.
        int ndx = model.indexOf(c);
        model.removeElement(c);
        if (ndx == model.getSize()) {
            --ndx;
        }
        controller.loadCategory(model.getElementAt(ndx).getID());
        categories.setSelectedIndex(ndx);
    }

    /**
     * Adds the specified {@link Category} into the list of possible
     * categories.
     *
     * @param newCat The category to add into the EditorCategorySelector
     */
    public void addCategory(Category newCat) {
        if (newCat == null)
            return;
        String newID = newCat.getID();
        if (idToCategory.get(newID) != null)
            return;

        model.addElement(newCat);
        idToCategory.put(newID, newCat);
        revalidate();
    }

    /**
     * Moves the selected Category up one and updates the list.
     *
     * @author Ashley Lu Couch
     */
    public void moveCategoryUp() {
        int index = categories.getSelectedIndex();
        if (index > 0 && index < model.size()) {
            Category temp = model.getElementAt(index);
            model.setElementAt(model.getElementAt(index - 1), index);
            model.setElementAt(temp, index - 1);
            categories.setSelectedIndex(index - 1);
        }
        revalidate();
    }

    /**
     * Moves the selected Category down one and updates the list.
     *
     * @author Ashley Lu Couch
     */
    public void moveCategoryDown() {
        int index = categories.getSelectedIndex();
        if (index >= 0 && index < model.size() - 1) {
            Category temp = model.getElementAt(index);
            model.setElementAt(model.getElementAt(index + 1), index);
            model.setElementAt(temp, index + 1);
            categories.setSelectedIndex(index + 1);
        }
        revalidate();
    }

    /**
     * Sets the EditorController that this class will communicate to when the
     * list is changed.
     *
     * @param controller The EditorController to be communicated with.
     */
    public void setController(EditorController controller) {
        this.controller = controller;
    }

    /**
     * Switches the focus of the list to a specific category.
     *
     * @param id The ID of the {@link Category} to select.
     */
    public void setFocus(String id) {
        Category c;
        if (id == null || (c = idToCategory.get(id)) == null)
            return;

        // Temporarily disable list change listening while swapping selection
        categories.removeListSelectionListener(this);
        categories.setSelectedIndex(model.indexOf(c));
        categories.addListSelectionListener(this);
        repaint();
    }

    /**
     * Informs the EditorController that a new Category is selected and should
     * be rendered in the main editor.
     *
     * @param e The list selection event.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() || categories.isSelectionEmpty()) {
            return;
        }
        int ndx = categories.getSelectedIndex();
        controller.loadCategory(model.getElementAt(ndx).getID());
    }
}
