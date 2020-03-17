package to.us.resume_builder.editorview.categoryEditPanes;

import to.us.resume_builder.resume_components.category.HeaderCategory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class HeaderCategoryEditPane extends CategoryEditPane {
    private JTextField fields[];
    private HeaderCategory headerCategory;
    private JCheckBox visible;

    /**
     * Constructor for Category edit pane
     * @param hc
     */
    public HeaderCategoryEditPane(HeaderCategory hc) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        headerCategory = hc;

        // Button for removing Header Category
        visible = new JCheckBox("Visible", true);
        JPanel info = new JPanel(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.HORIZONTAL;

        fields = new JTextField[] {
            new JTextField(hc.getLink(), SwingConstants.LEFT),
            new JTextField(hc.getEmail(), SwingConstants.LEFT),
            new JTextField(hc.getAddress(), SwingConstants.LEFT),
            new JTextField(hc.getPhoneNumber(), SwingConstants.LEFT)
        };

        JLabel labels[] = {
            new JLabel("Link: ", SwingConstants.LEFT),
            new JLabel("Email: ", SwingConstants.LEFT),
            new JLabel("Address: ", SwingConstants.LEFT),
            new JLabel("Phone Number: ", SwingConstants.LEFT)
        };

        int xPos = 0;
        int yPos = 0;

        grid.gridwidth = 1;
        grid.gridx = xPos;
        grid.gridy = yPos++;
        info.add(visible, grid);

        for (int i = 0; i < labels.length; i++) {
            fields[i].setMinimumSize(new Dimension(200, fields[i].getHeight()));
            fields[i].setMaximumSize(new Dimension(this.getWidth(), fields[i].getHeight()));

            labels[i].setLabelFor(fields[i]);

            grid.gridwidth = 1;
            grid.weightx = 0;
            grid.gridx = xPos;
            grid.gridy = yPos++;
            info.add(labels[i], grid);

            grid.gridwidth = 2;
            grid.weightx = 1;
            grid.gridx = xPos + 1;
            info.add(fields[i], grid);
        }

        JScrollPane scrollPane = new JScrollPane(info);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Saves information in each of the fields
     */
    @Override
    public void save() {
        headerCategory.setLink(fields[0].getText());
        headerCategory.setAddress(fields[1].getText());
        headerCategory.setEmail(fields[2].getText());
        headerCategory.setPhoneNumber(fields[3].getText());
        headerCategory.setVisible(visible.isSelected());
    }
}
