package to.us.resume_builder.resume_components;

import to.us.resume_builder.resume_components.category.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class Resume {

    /**
     * the list of categories that are associated with this resume.
     */
    private List<Category> categoryList;

    /**
     * Resume constructor creates a new instance of a resume.
     */
    public Resume() {
        categoryList = new LinkedList<>();
    }

    /**
     * add a Category of type to categoryList with a unique random generated
     * id.
     *
     * @param type the type of category to be created.
     *
     * @return the unique random generated id.
     */
    public String createCategory(CategoryType type) {
        String id;
        // generate a random id and check that it is not being used
        Random rand = new Random();
        do {
            id = String.valueOf(rand.nextInt(1000));
        } while (checkCategoryListID(id));

        // create a new category of type with the generated id
        switch (type) {
            case HEADER:
                categoryList.add(new HeaderCategory(id));
                break;
            case TEXT:
                categoryList.add(new TextCategory(id));
                break;
            case EXPERIENCE:
                categoryList.add(new ExperienceCategory(id));
                break;
            case BULLETS:
                categoryList.add(new BulletCategory(id));
                break;
        }
        return id;
    }

    /**
     * Get the current List categoryList for this instance.
     *
     * @return the current categoryList
     */
    public List<Category> getCategoryList() {
        return categoryList;
    }

    /**
     * Returns the Category reference by the id.
     *
     * @param id the String to find the category.
     *
     * @return a reference to the Category if it is found, if not found return
     *     null.
     */
    public Category getCategoryByID(String id) {
        return categoryList.stream()
            .filter(c -> c.getID().equals(id))
            .findFirst()
            .orElse(null);
    }

    /**
     * A helper function to check if the id is use in the list currently.
     *
     * @param id the string to check by.
     *
     * @return true if the id is used, false if not.
     */
    private boolean checkCategoryListID(String id) {
        return getCategoryByID(id) != null;
    }

    /**
     * Removes the categoryList item that matches the id.
     *
     * @param id the ID used to find the Category to remove.
     */
    public void removeCategoryByID(String id) {
        categoryList.removeIf(b -> b.getID().equals(id));
    }
}
