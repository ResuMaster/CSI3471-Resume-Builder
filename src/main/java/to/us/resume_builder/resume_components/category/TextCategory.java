package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.export.ResumeTemplate;

public class TextCategory extends Category {

    private String text;

    public TextCategory(String id) {
        super(id, CategoryType.TEXT);
    }

    public String getText() {
        return text;
    }

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
