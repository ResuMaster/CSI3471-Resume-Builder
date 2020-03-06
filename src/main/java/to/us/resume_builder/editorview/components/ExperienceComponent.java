package to.us.resume_builder.editorview.components;

import to.us.resume_builder.resume_components.Experience;

import javax.swing.*;
import java.awt.*;


public class ExperienceComponent extends JPanel {
    private JTextField organization;
    private JTextField location;
    private JTextField date;
    private JTextField title;
    private BulletComponent bulletComponent;

    private Experience experience;

    /**
     * @param exp the experience to use to fill the fields and change when the
     *            time comes
     */
    public ExperienceComponent(Experience exp) {
        this.experience = exp;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Setup GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;

        // Create the labels
        JLabel[] labels = {
            new JLabel("Organization/School", SwingConstants.RIGHT),
            new JLabel("Location", SwingConstants.CENTER),
            new JLabel("Date", SwingConstants.CENTER),
            new JLabel("Position/Degree", SwingConstants.RIGHT)
        };

        // Create panels
        JPanel topPanel = new JPanel(new GridBagLayout());
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        this.add(topPanel);
        this.add(bottomPanel);
        this.add(new JSeparator(SwingConstants.HORIZONTAL));
        this.add(new BulletComponent(this.experience.getBulletList()));

        // Organization field
        gbc.gridy = 0;
        this.organization = new JTextField(exp.getOrganization());
        labels[0].setLabelFor(this.organization);

        gbc.gridx = 0;
        gbc.weightx = 0.0;
        topPanel.add(labels[0], gbc);

        gbc.gridx = 1;
        gbc.weightx = 5.0;
        topPanel.add(organization, gbc);

        // Title field
        gbc.gridy = 1;
        this.title = new JTextField(exp.getTitle());
        labels[3].setLabelFor(this.title);

        gbc.gridx = 0;
        gbc.weightx = 0.0;
        topPanel.add(labels[3], gbc);

        gbc.gridx = 1;
        gbc.weightx = 5.0;
        topPanel.add(this.title, gbc);

        // Location field
        gbc.gridx = 0;
        this.location = new JTextField(exp.getLocation());
        labels[1].setLabelFor(this.location);

        gbc.gridy = 0;
        bottomPanel.add(labels[1], gbc);

        gbc.gridy = 1;
        bottomPanel.add(this.location, gbc);

        // Date field
        gbc.gridx = 1;
        this.date = new JTextField(exp.getDate());
        labels[2].setLabelFor(this.date);

        gbc.gridy = 0;
        bottomPanel.add(labels[2], gbc);

        gbc.gridy = 1;
        bottomPanel.add(this.date, gbc);
    }

    public void save() {
        experience.setOrganization(organization.getText());
        experience.setLocation(location.getText());
        experience.setDate(date.getText());
        experience.setTitle(title.getText());
        bulletComponent.save();
    }
}