package to.us.resume_builder.export.template;

import to.us.resume_builder.resume_components.category.CategoryType;

import java.util.Map;

public class ResumeTemplate {
    private Map<CategoryType, StringTemplate> categoryTemplates;
    private StringTemplate experienceTemplate;
    private StringTemplate fieldTemplate;

    public ResumeTemplate(String templateName) {
        // TODO
    }

    public StringTemplate getCategoryTemplate(CategoryType type) throws Exception { // TODO: InvalidKeyException
        if (categoryTemplates.containsKey(type)) {
            return categoryTemplates.get(type).copy();
        } else {
            throw new Exception(""); // TODO: InvalidKeyException
        }
    }

    public StringTemplate getExperienceTemplate() {
        return experienceTemplate.copy();
    }

    public StringTemplate getFieldTemplate() {
        return fieldTemplate.copy();
    }
}
