package to.us.resume_builder.data.resume_components;

import to.us.resume_builder.data.resume_components.category.BulletCategory;
import to.us.resume_builder.data.resume_components.category.ExperienceCategory;
import to.us.resume_builder.data.resume_components.category.HeaderCategory;
import to.us.resume_builder.data.resume_components.category.TextCategory;

/**
 * Creates an interface for visiting the various Categories
 * <p>
 * Example of Design Pattern: Visitor (hence the name)
 * 
 * @author Micah
 */
public interface CategoryVisitor {
    /**
     * Visit a BulletCategory
     * 
     * @param c The BulletCategory to add functionality to.
     */
    void visit(BulletCategory c);

    /**
     * Visit a TextCategory
     * 
     * @param c The TextCategory to add functionality to.
     */
    void visit(TextCategory c);

    /**
     * Visit an ExperienceCategory
     * 
     * @param c The ExperienceCategory to add functionality to.
     */
    void visit(ExperienceCategory c);

    /**
     * Visit a HeaderCategory
     * 
     * @param c The HeaderCategory to add functionality to.
     */
    void visit(HeaderCategory c);
}
