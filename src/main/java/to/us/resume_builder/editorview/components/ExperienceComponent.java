package to.us.resume_builder.editorview.components;

import to.us.resume_builder.resume_components.Bullet;
import to.us.resume_builder.resume_components.Experience;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ExperienceComponent extends JPanel{
    private JTextField organization;
    private JTextField location;
    private JTextField date;
    private JTextField title;
    private JCheckBox visible;
    private JButton save;

    private Experience experience;

    /**
     *
     * @param exp the experience to use to fill the fields and change when the time comes
     */
    public ExperienceComponent(Experience exp) {
        this.experience = exp;
        this.setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.HORIZONTAL;

        JLabel [] labels = {
                new JLabel("Organization:"),
                new JLabel("Location:"),
                new JLabel("Date:"),
                new JLabel("Title:")};

        visible = new JCheckBox();
        visible.setSelected(exp.getVisible());

        organization = new JTextField(exp.getOrganization());
        labels[0].setLabelFor(organization);

        location = new JTextField(exp.getLocation());
        labels[1].setLabelFor(location);

        date = new JTextField(exp.getDate());
        labels[2].setLabelFor(date);

        title = new JTextField(exp.getTitle());
        labels[3].setLabelFor(title);

        grid.gridx = 1;
        grid.gridy = 1;
        grid.gridwidth = 1;
        add(labels[0], grid);

        grid.gridx = 2;
        add(organization, grid);

        grid.gridx = 1;
        grid.gridy = 2;
        add(labels[1], grid);

        grid.gridx = 2;
        add(location, grid);

        grid.gridx = 1;
        grid.gridy = 3;
        add(labels[2], grid);

        grid.gridx = 2;
        add(date, grid);

        grid.gridx = 1;
        grid.gridy = 4;
        add(labels[3], grid);

        grid.gridx = 2;
        add(title, grid);

        grid.gridx = 1;
        grid.gridy = 5;
        save = new JButton("Save");
        add(save, grid);

        grid.gridx = 0;
        grid.gridy = 1;
        grid.weightx = .5;
        add(visible, grid);

        setVisible(true);
    }

    public void save() {
        experience.setOrganization(organization.getText());
        experience.setLocation(location.getText());
        experience.setDate(date.getText());
        experience.setTitle(title.getText());
        experience.setVisible(visible.isSelected());
    }
}