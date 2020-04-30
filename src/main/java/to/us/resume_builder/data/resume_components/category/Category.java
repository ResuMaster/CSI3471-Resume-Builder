package to.us.resume_builder.data.resume_components.category;

import to.us.resume_builder.business.export_LaTeX.ILaTeXConvertable;
import to.us.resume_builder.data.resume_components.Bullet;
import to.us.resume_builder.data.resume_components.CategoryVisitor;
import to.us.resume_builder.data.resume_components.ResumeComponent;

import java.util.Objects;

/**
 * A class which holds all default methods for any {@link CategoryType}
 * @author Ashley Lu Couch
 */
public abstract class Category extends ResumeComponent implements ILaTeXConvertable {

    /**
     * The type of the category.
     */
    protected final CategoryType type;

    /**
     * The name of the category, it is used for internal and outline
     * information.
     */
    protected String name = "";
    /**
     * The title of the category, it is used in the resume document.
     */
    protected String displayName = "";

    /**
     * An abstract category constructor.
     *
     * @param id   The id of the current object.
     * @param type The type that the category is.
     */
    protected Category(String id, CategoryType type) {
        super(id);
        this.type = type;
    }

    /**
     * Returns the CategoryType type of the category for this instance.
     *
     * @return type
     */
    public CategoryType getType() {
        return type;
    }

    /**
     * Returns the current String displayName for this instance.
     *
     * @return The String displayName.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the String displayName for this instance.
     *
     * @param displayName The String to set displayName to.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the current String name for this instance.
     *
     * @return The String name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the String name.
     *
     * @param name The String to set name to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the object. In this case it returns
     * the name of the object.
     *
     * @return a string representation of the object, the name.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Allow a CategoryVisitor to visit this category.
     * 
     * @param v The visitor to this Category.
     */
    public abstract void accept(CategoryVisitor v);

}




