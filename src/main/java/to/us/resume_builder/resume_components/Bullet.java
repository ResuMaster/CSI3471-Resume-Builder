package to.us.resume_builder.resume_components;

import to.us.resume_builder.export.ILaTeXConvertable;

public class Bullet extends ResumeComponent implements ILaTeXConvertable {

    private String text;

    public Bullet(String id){
        super(id);
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
