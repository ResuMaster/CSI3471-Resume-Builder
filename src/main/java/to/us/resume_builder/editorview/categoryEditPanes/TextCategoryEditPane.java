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


    public TextCategoryEditPane(TextCategory tc) {
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        textCategory = tc;

        visible = new JCheckBox("Visible", true);

        GridBagConstraints grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.LINE_START;
        JPanel info = new JPanel();
        JLabel textLabel = new JLabel("Text: ", SwingConstants.LEFT);

        int xPos = 0;
        int yPos = 0;

        grid.gridx = xPos;
        grid.gridy = yPos++;
        info.add(visible, grid);

        text = new JTextArea(tc.getText());
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        textLabel.setLabelFor(text);

        grid.weightx = 0;
        grid.gridx = xPos;
        grid.gridy = yPos++;
        info.add(textLabel, grid);

        grid.gridy = yPos;
        grid.gridwidth = 2;
        info.add(text, grid);

        JScrollPane scrollPane = new JScrollPane(info, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane, grid);
    }

    @Override
    public void save() {
        textCategory.setText(text.getText());
        textCategory.setName(fields[0].getText());
        textCategory.setDisplayName(fields[1].getText());
        textCategory.setVisible(visible.isSelected());
    }
}