package to.us.resume_builder.presentation.components;

import to.us.resume_builder.presentation.IEncapsulatedEditor;
import to.us.resume_builder.data.resume_components.Experience;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is an editor for individual experiences.
 *
 * @author Matthew McCaskill
 * @author Brooklynn Stone
 */
public class ExperienceEditor extends JPanel implements IEncapsulatedEditor {
    private static final Logger LOG = Logger.getLogger(ExperienceEditor.class.getName());

    // Individual editor components
    /**
     * A text field to edit the Experience's organization.
     */
    private JTextField organization;
    /**
     * A text field to edit the Experience's location.
     */
    private JTextField location;
    /**
     * A text field to edit the Experience's date.
     */
    private JTextField date;
    /**
     * A text field to edit the Experience's title.
     */
    private JTextField title;

    /**
     * The bullet list associated with the Experience.
     */
    private BulletListEditor bulletListEditor;

    /**
     * The {@link Experience} being edited.
     */
    private Experience experience;

    /**
     * Boolean flag to determine whether the category has been edited.
     */
    private boolean modified;

    /**
     * Internal class which marks the editor as modified any time a text field
     * detects a change.
     */
    private static class ExperienceModifiedDocumentListener implements DocumentListener {
        private ExperienceEditor exp;

        private ExperienceModifiedDocumentListener(ExperienceEditor exp) {
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
     * Construct an <code>ExperienceEditor</code> corresponding to the given
     * {@link Experience}.
     *
     * @param exp The experience to be edited.
     */
    public ExperienceEditor(Experience exp) {
        // Initialize fields
        this.experience = exp;
        this.modified = false;

        // Set the layout of this JPanel
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

        // Create top panel
        JPanel topPanel = new JPanel(new GridBagLayout());
        this.add(topPanel);

        // Create middle panel
        JPanel middlePanel = new JPanel(new GridBagLayout());
        this.add(middlePanel);

        // Create a separator
        this.add(new JSeparator(SwingConstants.HORIZONTAL));

        // Create thee bullet list editor
        bulletListEditor = new BulletListEditor(this.experience);
        this.add(bulletListEditor);

        // Organization field
        gbc.gridy = 0;
        this.organization = new JTextField(exp.getOrganization());
        labels[0].setLabelFor(this.organization);
        this.organization.getDocument().addDocumentListener(new ExperienceModifiedDocumentListener(this));

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
        this.title.getDocument().addDocumentListener(new ExperienceModifiedDocumentListener(this));

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
        this.location.getDocument().addDocumentListener(new ExperienceModifiedDocumentListener(this));

        gbc.gridy = 0;
        middlePanel.add(labels[1], gbc);

        gbc.gridy = 1;
        middlePanel.add(this.location, gbc);

        // Date field
        gbc.gridx = 1;
        this.date = new JTextField(exp.getDate());
        labels[2].setLabelFor(this.date);
        this.date.getDocument().addDocumentListener(new ExperienceModifiedDocumentListener(this));

        gbc.gridy = 0;
        middlePanel.add(labels[2], gbc);

        gbc.gridy = 1;
        middlePanel.add(this.date, gbc);

        // Set the size of the ExperienceEditor
        this.setMaximumSize(new Dimension(2000, this.bulletListEditor.getPreferredSize().height + 100));
    }

    /**
     * Get the experience being edited by this editor.
     *
     * @return The experience being edited.
     */
    public Experience getExperience() {
        return this.experience;
    }

    /**
     * Save the temporary changes to the {@link Experience} to the reference
     * object.
     */
    @Override
    public void save() {
        LOG.logp(Level.INFO, ExperienceEditor.class.getName(), "save", "saving Experience to resume in memory");

        // Update the experience
        this.experience.setOrganization(organization.getText());
        this.experience.setLocation(location.getText());
        this.experience.setDate(date.getText());
        this.experience.setTitle(title.getText());

        // Save the bullet list
        this.bulletListEditor.save();

        // Mark as no longer modified
        this.modified = false;
    }

    @Override
    public boolean isModified() {
        return this.modified || bulletListEditor.isModified();
    }
}