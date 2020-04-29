package to.us.resume_builder.data.resume_components.category;

import java.util.logging.Logger;

import to.us.resume_builder.business.export_LaTeX.ResumeTemplate;
import to.us.resume_builder.business.util.MiscUtils;
import to.us.resume_builder.data.resume_components.CategoryVisitor;

public class TextCategory extends Category {
    private static Logger LOGGER = Logger.getLogger(TextCategory.class.getName());

    /**
     * The text that is displayed on the resume.
     */
    private String text = "";

    /**
     * Creates an instance of TextCategory with id.
     *
     * @param id the ID for this instance of Category.
     */
    public TextCategory(String id) {
        super(id, CategoryType.TEXT);
    }

    /**
     * Returns the current String text for the instance.
     *
     * @return text The String text in the category.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the String text.
     *
     * @param text the String to set text to.
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
            .replaceVariable("title", MiscUtils.escapeLaTeX(this.displayName))
            .replaceVariable("content", MiscUtils.escapeLaTeX(this.text))
            .toString(() -> LOGGER.info("Generated LaTeX for text category \"" + this.displayName + "\"."));
    }

    /**
     * Allow a CategoryVisitor to visit this TextCategory.
     * 
     * @param v The visitor to this TextCategory.
     */
    @Override
    public void accept(CategoryVisitor v) {
        v.visit(this);
    }
}
