package to.us.resume_builder.editorview;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import to.us.resume_builder.resume_components.category.Category;

public class EditorStage extends JPanel {
    private EditPane edit;

    public EditorStage() {
        //
    }

    /**
     * Displays the given category to edit.
     * 
     * @param toEdit
     */
    public void showInEditor(Category toEdit) {
        if (edit != null)
            remove(edit);

        // This switch controls creating different editors for each CategoryType
        // TODO Connect with A&B's editors
        switch (toEdit.getType()) {
        case BULLETS:
            break;
        case EXPERIENCE:
            break;
        case HEADER:
            break;
        case TEXT:
            break;
        default:
            edit = new ConcreteEditPane(toEdit.getDisplayName());
            break;
        }

        add(edit);
        revalidate();
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
            f.add(new EditorStage());
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.pack();
            f.setVisible(true);
        });
    }
}
