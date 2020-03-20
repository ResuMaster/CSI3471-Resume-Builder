package to.us.resume_builder.editorview;

import to.us.resume_builder.resume_components.Resume;
import to.us.resume_builder.resume_components.ResumeComponent;
import to.us.resume_builder.resume_components.category.Category;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jacob
 * @author Micah
 */
public class EditorCategorySelector extends JPanel implements ListSelectionListener {
    private JList<Category> categories;
    private DefaultListModel<Category> model;
    private Map<String, Category> idToCategory;
    private JScrollPane scroll;
    private EditorController controller = null;

    /**
     * Assumes you only can remove a
     *
     * @param id
     */
    public void removeCategory(String id) {
        if (categories.isSelectionEmpty() || model.getSize() == 1) {
            return;
        }
        Category c = idToCategory.get(id);
        if (c == null) {
            return;
        }
        int ndx = model.indexOf(c);
        model.removeElement(c);
        if (ndx == model.getSize()) {
            --ndx;
        }
        controller.loadCategory(model.getElementAt(ndx).getID());
        categories.setSelectedIndex(ndx);
    }

    public void setController(EditorController controller) {
        this.controller = controller;
    }

    public EditorCategorySelector(Resume r) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        model = new DefaultListModel<>();
        model.addAll(r.getCategoryList());
        categories = new JList<>(model);
        idToCategory = r.getCategoryList().stream().collect(Collectors.toMap(ResumeComponent::getID, v -> v));

        scroll = new JScrollPane();

        categories.addListSelectionListener(this);

        scroll.add(categories);
        scroll.getViewport().setView(categories);
        add(scroll);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() || categories.isSelectionEmpty()) {
            return;
        }
        int ndx = categories.getSelectedIndex();
        controller.loadCategory(model.getElementAt(ndx).getID());
    }
}
