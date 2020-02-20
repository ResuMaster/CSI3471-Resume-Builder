package to.us.resume_builder.export.template;

import to.us.resume_builder.ApplicationConfiguration;
import to.us.resume_builder.resume_components.category.CategoryType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public enum ResumeTemplate {
    DEFAULT("default");

    private StringTemplate latexTemplate;
    private Map<CategoryType, StringTemplate> categoryTemplates;
    private StringTemplate experienceTemplate;
    private StringTemplate fieldTemplate;
    private StringTemplate separatorTemplate;

    ResumeTemplate(String templateName) {
        String templateDirectory = ApplicationConfiguration.getInstance().get("templateDirectory");

        try {
            latexTemplate = new StringTemplate(Files.readString(Path.of(templateDirectory, templateName, "latex.tem")));
            experienceTemplate = new StringTemplate(Files.readString(Path.of(templateDirectory, templateName, "experience.tem")));
            fieldTemplate = new StringTemplate(Files.readString(Path.of(templateDirectory, templateName, "field.tem")));
            separatorTemplate = new StringTemplate(Files.readString(Path.of(templateDirectory, templateName, "field.tem")));
            categoryTemplates = new HashMap<>();
            for (CategoryType c: CategoryType.values()) {
                categoryTemplates.put(c, new StringTemplate(Files.readString(Path.of(templateDirectory, templateName, c.getTemplateFileName() + ".tem"))));
            }
        } catch (IOException e) {
            e.printStackTrace(); // TODO: send error message
        }
    }

    public StringTemplate getCategoryTemplate(CategoryType type) { // TODO: InvalidKeyException
        return categoryTemplates.get(type).copy();
    }

    public StringTemplate getExperienceTemplate() {
        return experienceTemplate.copy();
    }

    public StringTemplate getFieldTemplate() {
        return fieldTemplate.copy();
    }

    public StringTemplate getLatexTemplate() {
        return latexTemplate.copy();
    }

    public StringTemplate getSeparatorTemplate() {
        return separatorTemplate.copy();
    }
}
