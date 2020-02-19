package to.us.resume_builder.components;

public class TextCategory extends Category {

    String text;

    TextCategory(String id) {
        super(id, CategoryType.TEXT);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toLaTeXString() {
        return null;
    }
}
