package to.us.resume_builder.business.export_LaTeX;

import to.us.resume_builder.business.ApplicationConfiguration;
import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.category.CategoryType;
import to.us.resume_builder.data.resume_components.Experience;
import to.us.resume_builder.data.resume_components.Bullet;
import to.us.resume_builder.business.util.StringTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

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

    /**
     * The template for all LaTeX types generated for LaTeX exporting and
     * compilation
     */
    private StringTemplate latexTemplate;
    /**
     * Templates for each {@link CategoryType} in LaTeX form
     */
    private Map<CategoryType, StringTemplate> categoryTemplates;
    /**
     * Template for the LaTeX of an {@link Experience}
     */
    private StringTemplate experienceTemplate;
    /**
     * Template for a field of a {@link Bullet}
     */
    private StringTemplate fieldTemplate;
    /**
     * Template for a solid line on the {@link to.us.resume_builder.data.resume_components.Resume}
     */
    private StringTemplate separatorTemplate;

    /**
     * Construct a <code>ResumeTemplate</code> from a template name (found in
     *
     * @param templateName The name of the template to load.
     */
    ResumeTemplate(String templateName) {
        final Logger LOGGER = Logger.getLogger(ResumeTemplate.class.getName());

        // Attempt to load the template files
        try {
            latexTemplate = new StringTemplate(readTemplate(templateName, "latex.tem"));
            experienceTemplate = new StringTemplate(readTemplate(templateName, "experience.tem"));
            fieldTemplate = new StringTemplate(readTemplate(templateName, "field.tem"));
            separatorTemplate = new StringTemplate(readTemplate(templateName, "separator.tem"));
            categoryTemplates = new HashMap<>();
            for (CategoryType c : CategoryType.values()) {
                categoryTemplates.put(c, new StringTemplate(readTemplate(templateName, c.getTemplateFileName() + ".tem")));
            }

            LOGGER.info("Read template files for template " + templateName);
        } catch (IOException e) {
            latexTemplate = new StringTemplate("");
            experienceTemplate = new StringTemplate("");
            fieldTemplate = new StringTemplate("");
            separatorTemplate = new StringTemplate("");
            categoryTemplates = new HashMap<>();
            for (CategoryType c : CategoryType.values()) {
                categoryTemplates.put(c, new StringTemplate(""));
            }

            LOGGER.severe("Could not read template files for template " + templateName);

            e.printStackTrace();
        }
    }

    private String readTemplate(String templateName, String fileName) throws IOException {
        StringBuilder template = new StringBuilder();
        URL path = Thread.currentThread().getContextClassLoader().getResource("templates/" + templateName + "/" + fileName);
        try (InputStream in = path.openStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                template.append(line).append("\n");
            }
        }

        return template.toString();
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
