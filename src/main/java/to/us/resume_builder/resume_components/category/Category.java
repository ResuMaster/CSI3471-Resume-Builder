package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.export.ILaTeXConvertable;
import to.us.resume_builder.resume_components.ResumeComponent;

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
     * Returns a string representation of the object. In general, the {@code
     * toString} method returns a string that "textually represents" this
     * object. The result should be a concise but informative representation
     * that is easy for a person to read. It is recommended that all subclasses
     * override this method.
     * <p>
     * The {@code toString} method for class {@code Object} returns a string
     * consisting of the name of the class of which the object is an instance,
     * the at-sign character `{@code @}', and the unsigned hexadecimal
     * representation of the hash code of the object. In other words, this
     * method returns a string equal to the value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
