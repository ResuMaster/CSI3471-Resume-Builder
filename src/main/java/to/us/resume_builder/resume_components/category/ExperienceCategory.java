package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.resume_components.Experience;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ExperienceCategory extends Category {

    /**
     * A list to hold the experiences for this category.
     */
    private List<Experience> experiences;

    /**
     * Creates an instance of ExperienceCategory with id.
     * @param id the ID for this instance of Category
     */
    public ExperienceCategory(String id) {
        super(id, CategoryType.EXPERIENCE);
        experiences = new LinkedList<>();
    }

    /**
     * Return the experience that matches the passed String id
     * @param id the String to compare to
     * @return a reference to the Experience if it is found, if not found return null.
     */
    Experience getExperienceByID(String id){
        return experiences.stream()
                .filter(c -> c.getID().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds an experience with a random generated id.
     * @return id the id of the new experience
     */
    public String addExperience(){
        do {
            // generate id with current id in the front
            Random rand = new Random();
            String id = this.id + "." + rand.nextInt(1000);
        } while (checkExperienceListID(id));
        experiences.add(new Experience(id));
        return id;
    }

    /**
     * Removes the list item that matches the id.
     * @param id
     */
    public void removeExperience(String id){
        experiences = experiences.stream().filter(e -> !e.getID().equals(id)).collect(Collectors.toList());
    }

    /**
     * Returns true if the id is found in the experiences list, false if not found
     * @param id the string to see if it is equal to any id's in list
     * @return true if found
     */
    private boolean checkExperienceListID(String id){
        return getExperienceByID(id) != null;
    }

    @Override
    public String toLaTeXString() {
        return null;
    }



}
