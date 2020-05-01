package to.us.resume_builder.data.resume_components.category;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Creates Categories based off of a provided {@link CategoryType}.
 * 
 * @author Micah
 */
public class CategoryFactory {
    /**
     * Logs when an Exception is thrown in the getInstance method
     */
    private static final Logger LOG = Logger.getLogger(CategoryFactory.class.getName());

    /**
     * The Factory which will be instantiated only once
     */
    private static CategoryFactory fac = null;

    /**
     * An object to Synchronize with to ensure there is only one creation of a CategoryFactory
     */
    private static final Object LOCK = new Object();

    /** The list of prototypical instances to produce from */
    private Map<CategoryType, Class<? extends Category>> clazzes;

    /**
     * Provide access to the factory to create Categories
     * 
     * @return The singleton CategoryFactory instance.
     */
    public static CategoryFactory getInstance() {
        if (fac == null) {
            synchronized (LOCK) {
                if (fac == null)
                    fac = new CategoryFactory();
            }
        }
        return fac;
    }

    /**
     * Initialize the set of possible categories to create.
     */
    private CategoryFactory() {
        clazzes = new HashMap<>();
        clazzes.put(CategoryType.BULLETS, BulletCategory.class);
        clazzes.put(CategoryType.EXPERIENCE, ExperienceCategory.class);
        clazzes.put(CategoryType.HEADER, HeaderCategory.class);
        clazzes.put(CategoryType.TEXT, TextCategory.class);
    }

    /**
     * Public interface for creating a Category. May be fleshed out with more
     * constants to creation later if need be.
     * <p>
     * Example of Design Pattern: Factory Method
     * 
     * @param type The type of the category to create
     * @param id   The ID of the category to create
     * @return A category matching the given descriptions.
     */
    public final Category createCategory(CategoryType type, String id) {
        Category c = getInstance(type, id);
        return c;
    }

    /**
     * Construct and return the requested Category type. Dynamically binds to allow
     * this method to be overridden in subclasses, to create custom instantiation
     * while maintaining a constant order of operations.
     * 
     * @param type The type of the category to create.
     * @param id   The ID of the category to create.
     * @return A new category with the given ID matching the given type. Should
     *         never return null.
     */
    protected Category getInstance(CategoryType type, String id) {
        Category c = null;
        var clazz = clazzes.get(type);
        try {
            if (clazz == null)
                throw new IllegalArgumentException(String.format("Type %1$s not a valid CategoryType!", type));
            c = clazz.getConstructor(String.class).newInstance(id);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | SecurityException e) {
            // Failure, create a placeholder category
            LOG.logp(Level.SEVERE, CategoryFactory.class.getName(), "getInstance",
                    "Reflection error occurred: " + e.getLocalizedMessage(), e);
            c = new TextCategory(id);
            ((TextCategory) c).setText("An error has occurred. Our deepest apologies.");
        }
        return c;
    }
}
