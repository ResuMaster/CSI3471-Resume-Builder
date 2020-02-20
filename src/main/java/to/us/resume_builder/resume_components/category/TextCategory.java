package to.us.resume_builder.resume_components.category;

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

    @Override
    public String getLaTeXContent() {
        return null;
    }
}
