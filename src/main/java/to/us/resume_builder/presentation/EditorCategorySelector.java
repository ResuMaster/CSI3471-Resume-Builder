package to.us.resume_builder.presentation;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.ResumeComponent;
import to.us.resume_builder.data.resume_components.category.Category;
import to.us.resume_builder.data.resume_components.category.CategoryType;
import to.us.resume_builder.presentation.controllers.EditorController;

/**
 * Selector for the Editor Category List. Controlled by an EditorController.
 *
 * @author Jacob Curtis
 * @author Micah Schiewe
 */
public class EditorCategorySelector extends JPanel implements ListSelectionListener {
    /**
     * Logs adding and removing {@link Category}'s
     */
    private static final Logger LOG = Logger.getLogger(EditorCategorySelector.class.getName());

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
        LOG.logp(Level.INFO, EditorCategorySelector.class.getName(), "removeCategory", "removing category with id: " + id);

        // Return if no selection or only one category available
        if (categories.isSelectionEmpty()) {
            JOptionPane.showMessageDialog(null, "No section selected! Please select a section in the list on the left.", "Couldn't Remove Section", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (model.getSize() == 1) {
            JOptionPane.showMessageDialog(null, "You must have at least one section.", "Couldn't Remove Section", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the Category for the iD
        Category c = idToCategory.get(id);
        if (c == null) {
            LOG.logp(Level.WARNING, EditorCategorySelector.class.getName(), "removeCategory", "could not find category id: " + id);
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
        controller.saveCurrentCategoryOrder();
    }

    /**
     * Adds the specified {@link Category} into the list of possible
     * categories.
     *
     * @param newCat The category to add into the EditorCategorySelector
     */
    public void addCategory(Category newCat) {
        LOG.logp(Level.INFO, EditorCategorySelector.class.getName(), "removeCategory", "adding new category");
        if (newCat == null)
            return;
        String newID = newCat.getID();
        if (idToCategory.get(newID) != null)
            return;

        if (newCat.getType() == CategoryType.HEADER) {
            model.insertElementAt(newCat, (int) idToCategory.values().stream().filter(c -> c.getType() == CategoryType.HEADER).count());
        } else {
            model.addElement(newCat);
        }
        idToCategory.put(newID, newCat);
        revalidate();
        controller.saveCurrentCategoryOrder();
    }

    /**
     * Moves the selected Category up one and updates the list.
     *
     * @author Ashley Lu Couch
     */
    public void moveCategoryUp() {
        int index = categories.getSelectedIndex();
        if (index > 0 && index < model.size()) {
            if ((model.getElementAt(index - 1).getType() == CategoryType.HEADER &&
                model.getElementAt(index).getType() == CategoryType.HEADER) ||
                model.getElementAt(index - 1).getType() != CategoryType.HEADER) {
                Category temp = model.getElementAt(index);
                model.setElementAt(model.getElementAt(index - 1), index);
                model.setElementAt(temp, index - 1);
                categories.setSelectedIndex(index - 1);
            }
        }
        revalidate();
        controller.saveCurrentCategoryOrder();
    }

    /**
     * Moves the selected Category down one and updates the list.
     *
     * @author Ashley Lu Couch
     */
    public void moveCategoryDown() {
        int index = categories.getSelectedIndex();
        if (index >= 0 && index < model.size() - 1) {
            if ((model.getElementAt(index + 1).getType() == CategoryType.HEADER &&
                model.getElementAt(index).getType() == CategoryType.HEADER) ||
                categories.getModel().getElementAt(index).getType() != CategoryType.HEADER) {
                Category temp = model.getElementAt(index);
                model.setElementAt(model.getElementAt(index + 1), index);
                model.setElementAt(temp, index + 1);
                categories.setSelectedIndex(index + 1);
            }
        }
        revalidate();
        controller.saveCurrentCategoryOrder();
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

    /**
     * Returns the model of this <code>EditorCategorySelector</code>.
     *
     * @return The model of this <code>EditorCategorySelector</code>.
     */
    public DefaultListModel<Category> getModel() {
        return model;
    }
}
