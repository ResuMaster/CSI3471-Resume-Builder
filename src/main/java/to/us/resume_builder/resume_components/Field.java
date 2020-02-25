package to.us.resume_builder.resume_components;

import to.us.resume_builder.export.ILaTeXConvertable;
import to.us.resume_builder.export.ResumeTemplate;

public class Field extends ResumeComponent implements ILaTeXConvertable {

    private String text;

    public Field(String id){
        super(id);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String formatLaTeXString(ResumeTemplate template)  {
        return template.getFieldTemplate()
            .replaceVariable("content", this.text)
            .toString();
    }
}
