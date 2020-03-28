package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.export.ResumeTemplate;
import to.us.resume_builder.resume_components.Bullet;
import to.us.resume_builder.resume_components.Experience;
import to.us.resume_builder.resume_components.ResumeComponent;
import to.us.resume_builder.util.MiscUtils;

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
     *
     * @param id The ID for this instance of Category.
     */
    public ExperienceCategory(String id) {
        super(id, CategoryType.EXPERIENCE);
        experiences = new LinkedList<>();
    }

    /**
     * Return the experience that matches the passed String id.
     *
     * @param id The String to compare to.
     *
     * @return A reference to the Experience if it is found, if not found return
     *     null.
     */
    public Experience getExperienceByID(String id) {
        return experiences.stream()
            .filter(c -> c.getID().equals(id))
            .findFirst()
            .orElse(null);
    }

    /**
     * Adds an experience with a random generated id.
     *
     * @return The id of the new experience.
     */
    public String addExperience() {
        String id;
        Random rand = new Random();
        do {
            // generate id with current id in the front
            id = this.id + "." + rand.nextInt(1000);
        } while (checkExperienceListID(id));
        experiences.add(new Experience(id));
        return id;
    }

    /**
     * Adds an experience with a random generated id.
     *
     * @param e The experience that is being copied.
     *
     * @return The id of the new experience.
     */
    public String addExperience(Experience e) {
        String id;
        do {
            // generate id with current id in the front
            Random rand = new Random();
            id = this.id + "." + rand.nextInt(1000);
        } while (checkExperienceListID(id));

        Experience copy = new Experience(id);
        copy.setVisible(e.getVisible());
        copy.setTitle(e.getTitle());
        copy.setDate(e.getDate());
        copy.setLocation(e.getLocation());
        copy.setOrganization(e.getOrganization());
        for (Bullet b : e.getBulletList()) {
            copy.getBulletByID(copy.addBullet()).setText(b.getText());
        }
        experiences.add(copy);
        return id;
    }

    /**
     * Removes the list item that matches the id.
     *
     * @param id The String find the instance to remove by.
     */
    public void removeExperience(String id) {
        experiences.removeIf(b -> b.getID().equals(id));
    }

    /**
     * Returns true if the id is found in the experiences list, false if not
     * found.
     *
     * @param id The string to see if it is equal to any id's in list.
     *
     * @return True if found.
     */
    private boolean checkExperienceListID(String id) {
        return getExperienceByID(id) != null;
    }

    /**
     * Get the current List experienceList for this instance.
     *
     * @return the current experienceList
     */
    public List<Experience> getExperienceList() {
        return experiences;
    }

    /**
     * Get the result of serializing this object using the specified template.
     *
     * @param template The template to format this object with.
     *
     * @return A String representing the object in the LaTeX template.
     * @author Matthew McCaskill
     */
    @Override
    public String formatLaTeXString(ResumeTemplate template) {
        if (this.experiences == null) {
            this.experiences = new LinkedList<>();
        }

        return template.getCategoryTemplate(this.type)
            .replaceVariable("title", MiscUtils.escapeLaTeX(this.displayName))
            .replaceVariable("content", experiences.stream()
                .filter(ResumeComponent::getVisible)
                .map(f -> f.formatLaTeXString(template))
                .reduce((a, b) -> a + b)
                .orElse("")
            )
            .toString();
    }


}
