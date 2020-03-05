package to.us.resume_builder.editorview;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import to.us.resume_builder.resume_components.category.Category;
import to.us.resume_builder.resume_components.category.TextCategory;

/**
 * @author Jaocb
 * @author Micah
 */
public class EditorStage extends JPanel {
    private JPanel editContainer;
    private EditorCategoryHeader header;
    private EditPane edit;
    private EditorController controller = null;
    private Category category;

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
        saveButton.addActionListener(e -> edit.save());
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Assemble.
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(scroller);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(saveButton);
    }

    /**
     * Displays the given category to edit.
     * 
     * @param toEdit
     */
    public void showInEditor(Category toEdit) {
        if (edit != null)
            editContainer.remove(edit);
        category = toEdit;
        header.updateHeader(toEdit);
        edit = getEditor(toEdit);
        editContainer.add(edit);
        revalidate();
    }

    /**
     * Generates the proper editor component for the provided category.
     * 
     * @param toEdit The category to get an editor for
     * @return A GUI editor for the provided {@link Category}
     */
    private EditPane getEditor(Category toEdit) {
        EditPane editPane = null;
        // This switch controls creating different editors for each CategoryType
        // TODO Connect with A&B's editors
        switch (toEdit.getType()) {
//        case BULLETS:
//            editPane = new BulletCategoryEditPane(toEdit);
//            break;
//        case EXPERIENCE:
//            editPane = new ExperienceCategoryEditPane();
//            break;
//        case HEADER:
//            editPane = new HeaderCategoryEditPane();
//            break;
//        case TEXT:
//            editPane = new TextCategoryEditPane();
//            break;
        default:
            editPane = new ConcreteEditPane(toEdit.getDisplayName());
            break;
        }

        return editPane;
    }

    public void setController(EditorController controller) {
        this.controller = controller;
    }

    /**
     * A dummy class to facilitate testing. Implements the EditPane functionality.
     * 
     * @author Micah
     */
    private class ConcreteEditPane extends EditPane {
        ConcreteEditPane(String text) {
            JLabel label = new JLabel(text);
            add(label);
        }

        @Override
        public void save() {
            JOptionPane.showMessageDialog(this, "Dummy save message.");
        }
    }

}
