package to.us.resume_builder.components;

import to.us.resume_builder.export.ILaTeXConvertable;

public class Category implements ILaTeXConvertable {

    final String id;
    final CategoryType type;

    boolean visible = true;

    /**
     * The name of the category, it is used for internal and outline information.
     */
    String name;
    /**
     * The title of the category, it is used in the resume document.
     */
    String displayName;

    Category(String id, CategoryType type){
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
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

    public CategoryType getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toLaTeXString() {
        return null;
    }
}
