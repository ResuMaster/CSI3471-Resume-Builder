package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.export.ResumeTemplate;
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

    public Experience getExperience(String id) {
        return experiences.stream()
            .filter(c -> c.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    /**
     * Get the result of serializing this object using the specified template.
     *
     * @param template The template to format this object with.
     *
     * @return A String representing the object in the LaTeX template.
     * @author Matthew McCaskill
     */
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
