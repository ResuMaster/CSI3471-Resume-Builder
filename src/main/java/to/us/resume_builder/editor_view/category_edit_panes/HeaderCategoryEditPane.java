package to.us.resume_builder.editor_view.category_edit_panes;

import to.us.resume_builder.resume_components.category.HeaderCategory;

import javax.swing.*;
import java.awt.*;


/**
 * Facilitates editing of the HeaderCategory resume component.
 *
 * @author Brooklyn Stone
 */
public class HeaderCategoryEditPane extends CategoryEditPane {
    /**
     * An array of each of the editable Text Fields for a Header Category.
     *      0: Link
     *      1: Email
     *      2: Address
     *      3: Phone Number
     */
    private JTextField fields[];
    /**
     * The Header Category to display
     */
    private HeaderCategory headerCategory;

    /**
     * Constructor for Header Category edit pane which displays each of the Header
     * fields in a UI
     * @param hc
     */
    public HeaderCategoryEditPane(HeaderCategory hc) {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        headerCategory = hc;

        // Button for removing Header Category
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
        this.add(scrollPane, BorderLayout.NORTH);
    }

    /**
     * Saves information in each of the fields of the Header Category Edit Pane
     * to the Header Category
     */
    @Override
    public void save() {
        headerCategory.setLink(fields[0].getText());
        headerCategory.setAddress(fields[1].getText());
        headerCategory.setEmail(fields[2].getText());
        headerCategory.setPhoneNumber(fields[3].getText());
    }

    /**
     * Determines if the current Header Category has been modified
     * @return boolean indicating whether the Category was edited by the user
     */
    public boolean isModified() {
        if(headerCategory.getLink().equals(fields[0].getText()) &&
            headerCategory.getEmail().equals(fields[1].getText()) &&
            headerCategory.getAddress().equals(fields[2].getText()) &&
            headerCategory.getPhoneNumber().equals(fields[3].getText()))
            return false;
        return true;
    }
}
