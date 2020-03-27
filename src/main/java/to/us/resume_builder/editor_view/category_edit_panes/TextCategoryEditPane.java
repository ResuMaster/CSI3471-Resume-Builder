package to.us.resume_builder.editor_view.category_edit_panes;

import to.us.resume_builder.resume_components.category.TextCategory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Facilitates editing of the TextCategory resume component.
 *
 * @author Brooklynn Stone
 * @author Micah Schiewe
 */
public class TextCategoryEditPane extends CategoryEditPane {
    /**
     * Holds the Text Category information for this resume in the UI
     */
    private JTextArea text;
    /**
     * The Text Category to display
     */
    private TextCategory textCategory;

    /**
     * Constructs a Text Category Edit Pane in a JPanel with a JTextArea that is
     * editable
     *
     * @param tc the Text Category that is being display in the Edit Pane
     */
    public TextCategoryEditPane(TextCategory tc) {
        // Prepare to create UI
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        textCategory = tc;

        // Create UI parts
//        JLabel label = new JLabel("Text:");
        text = new JTextArea(tc.getText());
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        text.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        // Add label to panel
//        gbc.gridx = 0;
//        gbc.gridy = 0;git
//        gbc.gridwidth = 1;
//        gbc.gridheight = 1;
//        gbc.weightx = 0;
//        gbc.weighty = 0;
//        add(label, gbc);

        // Create text area's size moderator
        TextFieldWidthModerator mod = new TextFieldWidthModerator(text);
        text.addComponentListener(mod);
        text.getDocument().addDocumentListener(mod);

        // Add text area to panel
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.gridheight = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(text, gbc);
    }

    /**
     * Saves all changes made in the JTextArea to the Text Category
     */
    @Override
    public void save() {
        textCategory.setText(text.getText());
    }

    /**
     * <<<<<<< HEAD Determines if the current Category has been modified
     * <p>
     * ======= Determines if the current Text Category has been modified >>>>>>>
     * 8c217d6e764efde5bc3e2ccc1f21a3ed241ee3fe
     *
     * @return boolean indicating whether the Category was edited
     */
    public boolean isModified() {
        if (textCategory.getText().equals(text.getText()))
            return false;
        return true;
    }

    /**
     * Monitors a JTextArea and re-sizes it to fill the amount of horizontal
     * space required by its parent component.
     *
     * @author Micah
     */
    private class TextFieldWidthModerator extends ComponentAdapter implements DocumentListener {
        private JTextArea monitored;

        public TextFieldWidthModerator(JTextArea monitored) {
            super();
            this.monitored = monitored;
        }

        @Override
        public void componentResized(ComponentEvent e) {
            updateMonitoredSize();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateMonitoredSize();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateMonitoredSize();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateMonitoredSize();
        }

        /**
         * Updates the size of the monitored JTextArea to make it fill only as
         * much horizontal space as its parent component decides for it.
         */
        private void updateMonitoredSize() {
            int prefHeight = (int) monitored.getMinimumSize().getHeight();
            monitored.setPreferredSize(new Dimension(0, prefHeight));
        }
    }
}
