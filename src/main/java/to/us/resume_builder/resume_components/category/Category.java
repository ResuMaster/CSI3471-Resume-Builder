package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.export.ILaTeXConvertable;
import to.us.resume_builder.resume_components.ResumeComponent;

public abstract class Category extends ResumeComponent implements ILaTeXConvertable {

    /**
     * The type of the category.
     */
    protected final CategoryType type;

    /**
     * The name of the category, it is used for internal and outline information.
     */
    protected String name;
    /**
     * The title of the category, it is used in the resume document.
     */
    protected String displayName;

    /**
     * An abstract category.
     * @param id the id of the current object
     * @param type the type that the category is
     */
    protected Category(String id, CategoryType type){
        super(id);
        this.type = type;
    }

    /**
     * Returns the CategoryType type of the category.
     * @return type
     */
    public CategoryType getType() {
        return type;
    }

    /**
     * Returns the current String displayName.
     * @return displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the String displayName.
     * @param displayName the String to set to
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the current String name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the String name.
     * @param name the String to set to
     */
    public void setName(String name) {
        this.name = name;
    }
}
