package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.export.template.ResumeTemplate;
import to.us.resume_builder.export.template.StringTemplate;

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
    public String formatLaTeXString(ResumeTemplate template) {
        return template.getCategoryTemplate(this.type)
            .replaceVariable("title", this.displayName)
            .replaceVariable("content", this.text)
            .toString();
    }
}
