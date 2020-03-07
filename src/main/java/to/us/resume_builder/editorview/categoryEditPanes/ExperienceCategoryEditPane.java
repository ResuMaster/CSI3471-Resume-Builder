package to.us.resume_builder.editorview.categoryEditPanes;

import to.us.resume_builder.editorview.components.ExperienceComponent;
import to.us.resume_builder.resume_components.Experience;
import to.us.resume_builder.resume_components.category.ExperienceCategory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ExperienceCategoryEditPane extends CategoryEditPane {
    private List<ExperienceComponent> experienceComponentList;
    private List<Experience> ref;
    private JPanel experienceList;

    public ExperienceCategoryEditPane(ExperienceCategory ec) {
        this.ref = ec.getExperienceList();
        this.experienceComponentList = new ArrayList<>();

        this.setLayout(new BorderLayout());

        JButton addButton = new JButton("Add Experience");
        addButton.addActionListener(e-> {
            this.experienceComponentList.add(new ExperienceComponent(ec.getExperienceByID(ec.addExperience())));
            this.updateExperienceListUI();
        });
        this.add(addButton, BorderLayout.PAGE_START);

        this.experienceList = new JPanel() {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(400, Arrays.stream(getComponents()).mapToInt(c -> c.getMaximumSize().height).sum());
            }
        };
        experienceList.setLayout(new BoxLayout(experienceList, BoxLayout.PAGE_AXIS));

        this.ref.forEach(e -> experienceComponentList.add(new ExperienceComponent(e)));

        updateExperienceListUI();

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(experienceList, BorderLayout.PAGE_START);

        JScrollPane scrollPane = new JScrollPane(wrapper);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void save() {
        this.ref.clear();
        this.ref.addAll(this.experienceComponentList.stream().map(ExperienceComponent::getExperience).collect(Collectors.toList()));
    }

    public void updateExperienceListUI() {
        experienceList.removeAll();

        for (int i = 0; i < experienceComponentList.size(); i++) {
            final int index = i;
            ExperienceComponent ec = experienceComponentList.get(i);

            JPanel experiencePanel = new JPanel();
            experiencePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            experiencePanel.setLayout(new BoxLayout(experiencePanel, BoxLayout.PAGE_AXIS));

            JPanel controlButtons = new JPanel();
            controlButtons.setLayout(new BoxLayout(controlButtons, BoxLayout.LINE_AXIS));

            // Visibility
            JCheckBox visibilityControl = new JCheckBox("Visible", true);
            visibilityControl.addItemListener(evt -> ec.getExperience().setVisible(evt.getStateChange() == ItemEvent.SELECTED));
            controlButtons.add(visibilityControl);

            // Remove experience
            JButton removeControl = new JButton("Remove Experience");
            removeControl.addActionListener(evt -> {
                this.experienceComponentList.remove(ec);
                experienceList.remove(experiencePanel);
                experienceList.updateUI();
            });
            controlButtons.add(removeControl);

            controlButtons.add(Box.createHorizontalGlue());

            // Move up
            JButton moveUpControl = new JButton("▲");
            moveUpControl.addActionListener(evt -> {
                if (index > 0) {
                    Collections.swap(this.experienceComponentList, index - 1, index);
                }

                updateExperienceListUI();
            });
            controlButtons.add(moveUpControl);

            // Move down
            JButton moveDownControl = new JButton("▼");
            moveDownControl.addActionListener(evt -> {
                if (index < this.experienceComponentList.size() - 1) {
                    Collections.swap(this.experienceComponentList, index + 1, index);
                    experienceList.updateUI();
                }

                updateExperienceListUI();
            });
            controlButtons.add(moveDownControl);

            experiencePanel.add(controlButtons);
            experiencePanel.add(ec);

            experienceList.add(experiencePanel);
        }

        experienceList.updateUI();
    }
}