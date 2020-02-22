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

public class EditorStage extends JPanel {
    private JPanel editContainer;
    private EditorCategoryHeader header;
    private EditPane edit;

    public EditorStage(Category startingCategory) {
        JButton saveButton;
        JScrollPane scroller;

        // Create central UI
        editContainer = new JPanel();
        editContainer.setLayout(new BoxLayout(editContainer, BoxLayout.Y_AXIS));

        header = new EditorCategoryHeader(startingCategory);
        edit = getEditor(startingCategory);
        editContainer.add(header);
        editContainer.add(edit);
        scroller = new JScrollPane(editContainer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Save button
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            edit.save();
        });
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Assemble.
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());
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
            remove(edit);
        edit = getEditor(toEdit);
        add(edit);
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
//            break;
//        case EXPERIENCE:
//            break;
//        case HEADER:
//            break;
//        case TEXT:
//            break;
        default:
            editPane = new ConcreteEditPane(toEdit.getDisplayName());
            break;
        }

        return editPane;
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
        void save() {
            JOptionPane.showMessageDialog(this, "Dummy save message.");
        }
    }

    /**
     * Temporary main for testing.
     * 
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Tester");
            f.add(new EditorStage(new TextCategory("heya")));
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.pack();
            f.setVisible(true);
        });
    }
}
