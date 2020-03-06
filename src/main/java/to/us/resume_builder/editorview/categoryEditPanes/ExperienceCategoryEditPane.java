package to.us.resume_builder.editorview.categoryEditPanes;

import to.us.resume_builder.editorview.components.ExperienceComponent;
import to.us.resume_builder.resume_components.Experience;
import to.us.resume_builder.resume_components.category.ExperienceCategory;

import javax.swing.*;
import java.util.List;

public class ExperienceCategoryEditPane extends CategoryEditPane {
    List<Experience> experienceList;

    public ExperienceCategoryEditPane(ExperienceCategory ec) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        experienceList = ec.getExperienceList();

        experienceList.forEach(e -> add(new ExperienceComponent(e)));
    }

    @Override
    public void save() {

    }
}