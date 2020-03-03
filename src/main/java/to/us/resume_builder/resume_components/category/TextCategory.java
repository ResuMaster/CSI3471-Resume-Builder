package to.us.resume_builder.resume_components.category;

public class TextCategory extends Category {

    /**
     * The text that is displayed on the resume.
     */
    private String text;

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

    @Override
    public String toLaTeXString() {
        return null;
    }
}
