package to.us.resume_builder.editorview.categoryEditPanes;

import to.us.resume_builder.editorview.components.ExperienceComponent;
import to.us.resume_builder.resume_components.Experience;
import to.us.resume_builder.resume_components.category.ExperienceCategory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ExperienceCategoryEditPane extends CategoryEditPane {
    List<Experience> experienceList;

    public ExperienceCategoryEditPane(ExperienceCategory ec) {
        this.setLayout(new BorderLayout());

        JButton addButton = new JButton("Add Experience");
        this.add(addButton, BorderLayout.PAGE_START);

        JPanel experienceList = new JPanel() {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(400, Arrays.stream(getComponents()).mapToInt(c -> c.getMaximumSize().height).sum());
            }
        };
        experienceList.setLayout(new BoxLayout(experienceList, BoxLayout.PAGE_AXIS));

        this.experienceList = ec.getExperienceList();

        this.experienceList.forEach(e -> {
            JPanel experiencePanel = new JPanel();
            experiencePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            experiencePanel.setLayout(new BoxLayout(experiencePanel, BoxLayout.PAGE_AXIS));

            JPanel controlButtons = new JPanel();
            controlButtons.setLayout(new BoxLayout(controlButtons, BoxLayout.LINE_AXIS));

            controlButtons.add(new JCheckBox("Visible", true));
            controlButtons.add(new JButton("Remove Experience"));
            controlButtons.add(Box.createHorizontalGlue());
            controlButtons.add(new JButton("▲"));
            controlButtons.add(new JButton("▼"));

            experiencePanel.add(controlButtons);
            experiencePanel.add(new ExperienceComponent(e));

            experienceList.add(experiencePanel);
            experienceList.add(new JSeparator(SwingConstants.HORIZONTAL));
        });

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(experienceList, BorderLayout.PAGE_START);

        JScrollPane scrollPane = new JScrollPane(wrapper);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void save() {

    }
}