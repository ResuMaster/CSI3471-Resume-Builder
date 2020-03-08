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
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        textCategory = tc;

        // Button for removing Header Category
        visible = new JCheckBox("Visible", true);
        JPanel info = new JPanel(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.HORIZONTAL;

        fields = new JTextField[] {
            new JTextField(tc.getName()),
            new JTextField(tc.getDisplayName())
        };

        JLabel labels[] = {
            new JLabel("Name: ", SwingConstants.LEFT),
            new JLabel("Display Name: ", SwingConstants.LEFT),
            new JLabel("Text: ", SwingConstants.LEFT)
        };

        int xPos = 0;
        int yPos = 0;

        grid.gridx = xPos;
        grid.gridy = yPos++;
        info.add(visible, grid);

        for (int i = 0; i < fields.length; i++) {
            fields[i].setMinimumSize(new Dimension(200, fields[i].getHeight()));
            fields[i].setMaximumSize(new Dimension(this.getWidth(), fields[i].getHeight()));

            labels[i].setLabelFor(fields[i]);

            grid.weightx = 0;
            grid.gridx = xPos;
            grid.gridy = yPos++;
            info.add(labels[i], grid);

            grid.weightx = 1;
            grid.gridx = xPos + 1;
            info.add(fields[i], grid);
        }

        text = new JTextArea(tc.getText());

//        text.setMinimumSize(new Dimension(200, text.getHeight()));
//        text.setMaximumSize(new Dimension(500, text.getHeight()));

        text.setLineWrap(true);
        labels[2].setLabelFor(text);

        grid.weightx = 0;
        grid.gridx = xPos;
        grid.gridy = yPos++;
        info.add(labels[2], grid);


        grid.gridy = yPos;
        grid.gridwidth = 2;
        info.add(text, grid);


        JScrollPane scrollPane = new JScrollPane(info);
        scrollPane.setMaximumSize(new Dimension(400, this.getHeight()));
        scrollPane.setMinimumSize(new Dimension(400, this.getHeight()));
        this.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void save() {
        textCategory.setText(text.getText());
        textCategory.setName(fields[0].getText());
        textCategory.setDisplayName(fields[1].getText());
        textCategory.setVisible(visible.isSelected());
    }
}