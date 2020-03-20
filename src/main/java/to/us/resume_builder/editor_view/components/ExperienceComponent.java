package to.us.resume_builder.editor_view.components;

import to.us.resume_builder.editor_view.IEncapsulatedEditor;
import to.us.resume_builder.resume_components.Experience;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 *
 * @author Matthew McCaskill
 * @author Brooklyn Stone
 */
public class ExperienceComponent extends JPanel implements IEncapsulatedEditor {
    private JTextField organization;
    private JTextField location;
    private JTextField date;
    private JTextField title;
    private BulletComponent bulletComponent;

    private Experience experience;
    private boolean modified;

    private static class ModifiedDocumentListener implements DocumentListener {
        private ExperienceComponent exp;

        private ModifiedDocumentListener(ExperienceComponent exp) {
            this.exp = exp;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            this.exp.modified = true;
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            this.exp.modified = true;
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            this.exp.modified = true;
        }
    }

    /**
     * @param exp the experience to use to fill the fields and change when the
     *            time comes
     */
    public ExperienceComponent(Experience exp) {
        this.experience = exp;
        this.modified = false;

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
        bulletComponent = new BulletComponent(this.experience);
        this.add(bulletComponent);

        // Organization field
        gbc.gridy = 0;
        this.organization = new JTextField(exp.getOrganization());
        labels[0].setLabelFor(this.organization);
        this.organization.getDocument().addDocumentListener(new ModifiedDocumentListener(this));

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
        this.title.getDocument().addDocumentListener(new ModifiedDocumentListener(this));

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
        this.location.getDocument().addDocumentListener(new ModifiedDocumentListener(this));

        gbc.gridy = 0;
        bottomPanel.add(labels[1], gbc);

        gbc.gridy = 1;
        bottomPanel.add(this.location, gbc);

        // Date field
        gbc.gridx = 1;
        this.date = new JTextField(exp.getDate());
        labels[2].setLabelFor(this.date);
        this.date.getDocument().addDocumentListener(new ModifiedDocumentListener(this));

        gbc.gridy = 0;
        bottomPanel.add(labels[2], gbc);

        gbc.gridy = 1;
        bottomPanel.add(this.date, gbc);

        this.setMaximumSize(new Dimension(2000, this.bulletComponent.getPreferredSize().height + 100));

    }

    public void save() {
        this.experience.setOrganization(organization.getText());
        this.experience.setLocation(location.getText());
        this.experience.setDate(date.getText());
        this.experience.setTitle(title.getText());
        this.bulletComponent.save();

        this.modified = false;
    }

    public boolean isModified() {
        return this.modified || bulletComponent.isModified();
    }

    public Experience getExperience() {
        return this.experience;
    }
}