package to.us.resume_builder.editorview.categoryEditPanes;

import to.us.resume_builder.resume_components.category.TextCategory;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class TextCategoryEditPane extends CategoryEditPane {
    private JTextArea text;
    private TextCategory textCategory;

    /**
     * Constructor for a Text Category Edit Pane
     * 
     * @param tc the Text Category that is being edited through the edit pane
     */
    public TextCategoryEditPane(TextCategory tc) {
        // Prepare to create UI
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        textCategory = tc;

        // Create UI parts
        JLabel label = new JLabel("Text:");
        text = new JTextArea(tc.getText());
        text.setWrapStyleWord(true);
        text.setLineWrap(true);

        // Add label to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        add(label, gbc);

        // Create text area's size moderator
        TextFieldSizeModerator mod = new TextFieldSizeModerator(text);
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
     * Saves all changes made in Text Edit Pane
     */
    @Override
    public void save() {
        textCategory.setText(text.getText());
    }
    
    private class TextFieldSizeModerator extends ComponentAdapter implements DocumentListener {
        private Component monitored;

        public TextFieldSizeModerator(Component monitored) {
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
        
        private void updateMonitoredSize() {
            int prefHeight = (int) monitored.getMinimumSize().getHeight();
            monitored.setPreferredSize(new Dimension(0, prefHeight));
        }
    }
}
