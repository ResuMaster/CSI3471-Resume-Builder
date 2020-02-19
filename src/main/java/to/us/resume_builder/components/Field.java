package to.us.resume_builder.components;

import to.us.resume_builder.export.ILaTeXConvertable;

public class Field implements ILaTeXConvertable {

    final String id;
    String text;
    boolean visible;

    Field(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getVisible(){
        return visible;
    }

    void toggleCategoryVisibility(){
        visible = !visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toLaTeXString() {
        return null;
    }
}
