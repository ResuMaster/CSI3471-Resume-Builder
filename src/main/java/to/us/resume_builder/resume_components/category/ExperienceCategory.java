package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.export.template.ResumeTemplate;
import to.us.resume_builder.export.template.StringTemplate;
import to.us.resume_builder.resume_components.Experience;

import java.util.LinkedList;
import java.util.List;

public class ExperienceCategory extends Category {

    private List<Experience> experiences;

    public ExperienceCategory(String id) {
        super(id, CategoryType.EXPERIENCE);
        experiences = new LinkedList<>();
    }

    // TODO Implement
    public String addExperience() {
        return "";
    }

    // TODO Implement
    public void removeExperience(String id) {

    }

    @Override
    public String formatLaTeXString(ResumeTemplate template) {
        return template.getCategoryTemplate(this.type)
            .replaceVariable("title", this.displayName)
            .replaceVariable("content", experiences.stream()
                .map(f -> f.formatLaTeXString(template))
                .reduce((a, b) -> a + b)
                .orElse("")
            )
            .toString();
    }
}
