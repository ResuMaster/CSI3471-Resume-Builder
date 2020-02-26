package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.export.ILaTeXConvertable;
import to.us.resume_builder.resume_components.ResumeComponent;

public abstract class Category extends ResumeComponent implements ILaTeXConvertable {

    protected final CategoryType type;

    /**
     * The name of the category, it is used for internal and outline information.
     */
    protected String name;
    /**
     * The title of the category, it is used in the resume document.
     */
    protected String displayName;

    protected Category(String id, CategoryType type){
        super(id);
        this.type = type;
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
