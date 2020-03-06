package to.us.resume_builder.editorview.categoryEditPanes;

import to.us.resume_builder.editorview.components.ExperienceComponent;
import to.us.resume_builder.resume_components.Experience;
import to.us.resume_builder.resume_components.category.ExperienceCategory;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ExperienceCategoryEditPane extends CategoryEditPane {
    List<Experience> experienceList;

    public ExperienceCategoryEditPane(ExperienceCategory ec) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(new JButton("Add Experience"));

        JPanel pane = new JPanel() {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(400, Arrays.stream(getComponents()).mapToInt(c -> c.getMaximumSize().height).sum());
            }
        };
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        experienceList = ec.getExperienceList();

        experienceList.forEach(e -> {
            JPanel experiencePanel = new JPanel();
            experiencePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            experiencePanel.setLayout(new BoxLayout(experiencePanel, BoxLayout.PAGE_AXIS));

            JPanel controlButtons = new JPanel();
            controlButtons.setLayout(new BoxLayout(controlButtons, BoxLayout.LINE_AXIS));

            controlButtons.add(new JCheckBox("Visible", true));
            controlButtons.add(new JButton("Remove Experience"));
            controlButtons.add(Box.createHorizontalGlue());
            controlButtons.add(new JButton("^"));
            controlButtons.add(new JButton("v"));

            experiencePanel.add(controlButtons);
            experiencePanel.add(new ExperienceComponent(e));

            pane.add(experiencePanel);
            pane.add(new JSeparator(SwingConstants.HORIZONTAL));
        });

        JScrollPane scrollPane = new JScrollPane(pane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane);
    }

    @Override
    public void save() {

    }
}