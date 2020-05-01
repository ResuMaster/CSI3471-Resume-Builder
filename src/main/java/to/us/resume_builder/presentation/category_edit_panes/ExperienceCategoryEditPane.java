package to.us.resume_builder.presentation.category_edit_panes;

import to.us.resume_builder.presentation.components.BulletListEditorTableModel;
import to.us.resume_builder.presentation.components.ExperienceEditor;
import to.us.resume_builder.data.resume_components.Experience;
import to.us.resume_builder.data.resume_components.category.ExperienceCategory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is an editor pane for experience categories.
 *
 * @author Matthew McCaskill
 */
public class ExperienceCategoryEditPane extends CategoryEditPane {
    /**
     * Logs updates and saves to the ExperienceCategoryEditPane
     */
    private static final Logger LOG = Logger.getLogger(ExperienceCategoryEditPane.class.getName());

    /**
     * The list of {@link Experience} editors.
     */
    private List<ExperienceEditor> experienceComponentList;

    /**
     * A reference to the {@link ExperienceCategory} being edited.
     */
    private transient ExperienceCategory ref;

    /**
     * The panel containing the list of {@link ExperienceEditor
     * ExperienceComponents}.
     */
    private JPanel experienceList;

    /**
     * Boolean flag to determine whether the category has been edited.
     */
    private boolean modified;

    /**
     * Constructs an <code>ExperienceCategoryEditPane</code> corresponding to an
     * {@link ExperienceCategory}.
     *
     * @param ec The {@link ExperienceCategory} to edit.
     */
    public ExperienceCategoryEditPane(ExperienceCategory ec) {
        // Initialize fields
        this.ref = ec;
        this.experienceComponentList = new ArrayList<>();
        this.modified = false;

        // Set the layout of this JPanel
        this.setLayout(new BorderLayout());

        // Add Experience button
        JButton addButton = new JButton("Add Experience");
        addButton.addActionListener(e -> {
            // Mark this component as modified
            this.modified = true;

            // Add an experience editor to the view
            this.experienceComponentList.add(new ExperienceEditor(new Experience("XXX")));
            this.updateExperienceListUI();
        });
        this.add(addButton, BorderLayout.PAGE_START);

        // Create the ExperienceEditor container
        this.experienceList = new JPanel() {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(400, Arrays.stream(getComponents()).mapToInt(c -> c.getMaximumSize().height).sum());
            }
        };
        experienceList.setLayout(new BoxLayout(experienceList, BoxLayout.PAGE_AXIS));
        experienceList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));

        // Create the ExperienceEditors
        this.ref.getExperienceList().forEach(e -> experienceComponentList.add(new ExperienceEditor(e)));
        updateExperienceListUI();

        // Create a wrapper panel for the ExperienceEditors to push them to the top of the panel
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(experienceList, BorderLayout.PAGE_START);

        // Create the ExperienceEditor scroll pane
        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Update and repaint the experience editor list.
     */
    public void updateExperienceListUI() {
        LOG.logp(Level.INFO, ExperienceCategoryEditPane.class.getName(), "updateExperienceListUI", "updating experience editor list and repainting");

        // Clear the list
        experienceList.removeAll();

        // Create the experience editor wrapper and controls
        for (int i = 0; i < experienceComponentList.size(); i++) {
            // Create a final int for use in the lambdas
            final int index = i;

            // Get the ExperienceEditor to be wrapped
            ExperienceEditor ec = experienceComponentList.get(i);

            // Create the experience panel
            JPanel experiencePanel = new JPanel();
            experiencePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            experiencePanel.setLayout(new BoxLayout(experiencePanel, BoxLayout.PAGE_AXIS));

            // Create the control buttons panel
            JPanel controlButtons = new JPanel();
            controlButtons.setLayout(new BoxLayout(controlButtons, BoxLayout.LINE_AXIS));

            // Visibility checkbox
            JCheckBox visibilityControl = new JCheckBox("Visible", true);
            visibilityControl.addItemListener(evt -> {
                // Mark this component as modified
                this.modified = true;

                // Store the visibility state
                ec.getExperience().setVisible(evt.getStateChange() == ItemEvent.SELECTED);
            });
            controlButtons.add(visibilityControl);

            // Remove Experience button
            JButton removeControl = new JButton("Remove Experience");
            removeControl.setBackground(new Color(255, 210, 210, 255));
            removeControl.addActionListener(evt -> {
                // Mark this component as modified
                this.modified = true;

                // Remove the specified experience and update the UI
                this.experienceComponentList.remove(ec);
                experienceList.remove(experiencePanel);
                experienceList.updateUI();
            });
            controlButtons.add(removeControl);

            // Add a spacer
            controlButtons.add(Box.createHorizontalGlue());

            // Move Up button
            JButton moveUpControl = new JButton("▲");
            moveUpControl.addActionListener(evt -> {
                if (index > 0) {
                    // Mark this component as modified
                    this.modified = true;

                    // Move up if possible
                    Collections.swap(this.experienceComponentList, index - 1, index);

                    // Repaint the UI
                    updateExperienceListUI();
                }
            });
            controlButtons.add(moveUpControl);

            // Move Down button
            JButton moveDownControl = new JButton("▼");
            moveDownControl.addActionListener(evt -> {
                if (index < this.experienceComponentList.size() - 1) {
                    // Mark this component as modified
                    this.modified = true;

                    // Move down if possible

                    Collections.swap(this.experienceComponentList, index + 1, index);

                    // Repaint the UI
                    updateExperienceListUI();
                }

            });
            controlButtons.add(moveDownControl);

            // Add the internal panels to the wrapper
            experiencePanel.add(controlButtons);
            experiencePanel.add(ec);

            // Add the experience panel to the list
            experienceList.add(experiencePanel);
            experienceList.add(Box.createVerticalStrut(10));
        }

        experienceList.updateUI();
    }

    /**
     * Save the temporary changes to the {@link ExperienceCategory} to the
     * reference object.
     */
    @Override
    public void save() {
        LOG.logp(Level.INFO, ExperienceCategoryEditPane.class.getName(), "save", "saving the ExperienceCategory changes");

        // Save each experience
        this.experienceComponentList.forEach(ExperienceEditor::save);

        // Update the experience list
        this.ref.getExperienceList().clear();
        this.experienceComponentList.forEach(e -> this.ref.addExperience(e.getExperience()));

        // Mark as no longer modified
        this.modified = false;
    }

    /**
     * Determine if the current {@link ExperienceCategory} has been modified.
     *
     * @return boolean indicating whether the Experience Category was edited
     */
    public boolean isModified() {
        return this.modified || this.experienceComponentList.stream().anyMatch(ExperienceEditor::isModified);
    }
}