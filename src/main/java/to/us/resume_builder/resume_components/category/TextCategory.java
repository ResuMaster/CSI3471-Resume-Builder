package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.export.ResumeTemplate;

public class TextCategory extends Category {

    /**
     * The text that is displayed on the resume
     */
    private String text;

    /**
     * Creates an instance of TextCategory with id
     * @param id the ID for this instance of Category
     */
    public TextCategory(String id) {
        super(id, CategoryType.TEXT);
    }

    /**
     * Returns the current String text
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the String text
     * @param text the String to set text to
     */
    public void setText(String text) {
        this.text = text;
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
            .replaceVariable("content", this.text)
            .toString();
    }
}
