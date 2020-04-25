package to.us.resume_builder.data.resume_components.category;

import java.util.Map;

public class CategoryPrototypeFactory {
    private static CategoryPrototypeFactory fac = null;
    private static final Object LOCK = new Object();

    /** The list of prototypical instances to produce from */
    private Map<CategoryType, Category> prototypes;

    /**
     * Provide access to the factory to create Categories
     * 
     * @return
     */
    public CategoryPrototypeFactory getInstance() {
        if (fac == null) {
            synchronized (LOCK) {
                if (fac == null)
                    fac = new CategoryPrototypeFactory();
            }
        }
        return fac;
    }

    private CategoryPrototypeFactory() {
        //
    }
}
