package to.us.resume_builder.editorview.categoryEditPanes;

import to.us.resume_builder.resume_components.category.HeaderCategory;
import to.us.resume_builder.resume_components.category.TextCategory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextCategoryEditPane extends CategoryEditPane {
    private JTextField fields[];
    private JTextArea text;
    private JCheckBox visible;
    private TextCategory textCategory;


    /**
     * Constructor for a Text Category Edit Pane
     * @param tc the Text Category that is being edited through the edit pane
     */
    public TextCategoryEditPane(TextCategory tc) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        GridBagConstraints grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.HORIZONTAL;

        textCategory = tc;
        visible = new JCheckBox("Visible", true);
        text = new JTextArea(tc.getText());
        text.setWrapStyleWord(true);
        text.setLineWrap(true);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));

        JScrollPane scrollPane = new JScrollPane(textPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JLabel textLabel = new JLabel("Text: ", SwingConstants.LEFT);

        grid.gridx = 0;
        grid.gridy = 0;
        infoPanel.add(visible, grid);

        grid.gridx = 0;
        grid.gridy = 1;
        infoPanel.add(textLabel, grid);

        textPanel.add(text);

        this.add(infoPanel);
        this.add(textPanel);
//        this.add(scrollPane);
    }

    /**
     * Saves all changes made in Text Edit Pane
     */
    @Override
    public void save() {
        textCategory.setText(text.getText());
        textCategory.setName(fields[0].getText());
        textCategory.setDisplayName(fields[1].getText());
        textCategory.setVisible(visible.isSelected());
    }
}