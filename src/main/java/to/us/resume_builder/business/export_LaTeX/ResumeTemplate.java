package to.us.resume_builder.business.export_LaTeX;

import to.us.resume_builder.business.ApplicationConfiguration;
import to.us.resume_builder.data.resume_components.category.CategoryType;
import to.us.resume_builder.business.util.StringTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides several LaTeX resume templates to export with. Current
 * types:
 * <ul>
 *     <li>DEFAULT: the default Baylor ECS resume style</li>
 * </ul>
 *
 * @author Matthew McCaskill
 */
public enum ResumeTemplate {
    DEFAULT("default"),
    DEFAULT_NO_HYPHENS("default-no-hyphens");

    private StringTemplate latexTemplate;
    private Map<CategoryType, StringTemplate> categoryTemplates;
    private StringTemplate experienceTemplate;
    private StringTemplate fieldTemplate;
    private StringTemplate separatorTemplate;

    /**
     * Construct a <code>ResumeTemplate</code> from a template name (found in
     *
     * @param templateName The name of the template to load.
     */
    ResumeTemplate(String templateName) {
        String templateDirectory = ApplicationConfiguration.getInstance().getString("templates.directory");

        // Attempt to load the template files
        try {
            latexTemplate = new StringTemplate(Files.readString(Path.of(templateDirectory, templateName, "latex.tem")));
            experienceTemplate = new StringTemplate(Files.readString(Path.of(templateDirectory, templateName, "experience.tem")));
            fieldTemplate = new StringTemplate(Files.readString(Path.of(templateDirectory, templateName, "field.tem")));
            separatorTemplate = new StringTemplate(Files.readString(Path.of(templateDirectory, templateName, "separator.tem")));
            categoryTemplates = new HashMap<>();
            for (CategoryType c : CategoryType.values()) {
                categoryTemplates.put(c, new StringTemplate(Files.readString(Path.of(templateDirectory, templateName, c.getTemplateFileName() + ".tem"))));
            }
        } catch (IOException e) {
            latexTemplate = new StringTemplate("");
            experienceTemplate = new StringTemplate("");
            fieldTemplate = new StringTemplate("");
            separatorTemplate = new StringTemplate("");
            categoryTemplates = new HashMap<>();
            for (CategoryType c : CategoryType.values()) {
                categoryTemplates.put(c, new StringTemplate(""));
            }

            e.printStackTrace(); // TODO: display error message
        }
    }

    /**
     * Get the template for the specified category.
     *
     * @param type The category to get the template for.
     *
     * @return The {@link StringTemplate} of the specified category.
     * @see StringTemplate
     */
    public StringTemplate getCategoryTemplate(CategoryType type) {
        return categoryTemplates.get(type).clone();
    }

    /**
     * Get the template for an experience.
     *
     * @return The {@link StringTemplate} of an experience.
     * @see StringTemplate
     */
    public StringTemplate getExperienceTemplate() {
        return experienceTemplate.clone();
    }

    /**
     * Get the template for a field.
     *
     * @return The {@link StringTemplate} of a field.
     * @see StringTemplate
     */
    public StringTemplate getFieldTemplate() {
        return fieldTemplate.clone();
    }

    /**
     * Get the template for the entire LaTeX file.
     *
     * @return The {@link StringTemplate} for the LaTeX prefix and suffix.
     * @see StringTemplate
     */
    public StringTemplate getLatexTemplate() {
        return latexTemplate.clone();
    }

    /**
     * Get the template for a separator.
     *
     * @return The {@link StringTemplate} of a separator.
     * @see StringTemplate
     */
    public StringTemplate getSeparatorTemplate() {
        return separatorTemplate.clone();
    }
}
