package to.us.resume_builder.data.resume_components;

import to.us.resume_builder.business.export_LaTeX.ILaTeXConvertable;
import to.us.resume_builder.business.export_LaTeX.ResumeTemplate;
import to.us.resume_builder.business.util.MiscUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Experience extends ResumeComponent implements ILaTeXConvertable, IBulletContainer {
    private static Logger LOGGER = Logger.getLogger(Experience.class.getName());

    /**
     * The name of the organization worked for, or the name of the school.
     */
    private String organization = "";

    /**
     * The location where work for organization took place.
     */
    private String location = "";

    /**
     * The date that the user want to display, it is a String and user is
     * responsible to format it.
     */
    private String date = "";

    /**
     * The position of the user, this is major for school or job title for
     * work.
     */
    private String title = "";

    /**
     * The list of bullets that follow an item.
     */
    private List<Bullet> bullets;

    /**
     * The number of columns for the bullet list
     */
    private int columnCount = 1;


    /**
     * Creates an instance of an item with id.
     *
     * @param id The id to set the experience to.
     */
    public Experience(String id) {
        super(id);
        bullets = new LinkedList<>();
    }

    /**
     * Get the current String organization for this instance.
     *
     * @return The current String for organization.
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Sets organization of this instance.
     *
     * @param organization The string to set organization to.
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * Get the current String location for this instance.
     *
     * @return The current String for location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location of this instance.
     *
     * @param location The string to set location to.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get the current String date for this instance.
     *
     * @return the current String date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date of this instance.
     *
     * @param date The string to set date to.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get the current String title for this instance.
     *
     * @return the current String title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title of this instance.
     *
     * @param title The string to set title to.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the current List of Bullets for this instance.
     *
     * @return The current Bullet List.
     */
    public List<Bullet> getBulletList() {
        return bullets;
    }

    /**
     * Set the number of columns in the bullet list.
     *
     * @param number The value to set columns to.
     */
    public void setColumn(int number) {
        this.columnCount = number;
    }

    /**
     * Get the number of columns in the bullet list.
     *
     * @return The number of columns.
     */
    public int getColumn() {
        return this.columnCount;
    }

    /**
     * Get the bullet component by id.
     *
     * @param id String to search for id.
     *
     * @return the bullet if found, or null if not found.
     */
    public Bullet getBulletByID(String id) {
        return bullets.stream()
            .filter(b -> b.getID().equals(id))
            .findFirst()
            .orElse(null);
    }

    /**
     * Create a new bullet for bullets list with a random generated id.
     *
     * @return the id created for the new bullet.
     */
    public String addBullet() {
        String id;
        Random rand = new Random();
        do {
            // generate id with current id in the front
            id = this.id + "." + rand.nextInt(1000);
        } while (checkBulletListID(id));

        // add new element to bullets
        bullets.add(new Bullet(id));
        return id;
    }

    /**
     * Copy a new bullet for bullets list with a random generated id.
     *
     * @return The id created for the new bullet.
     */
    public String addBullet(Bullet b) {
        String id;
        Random rand = new Random();
        do {
            // generate id with current id in the front
            id = this.id + "." + rand.nextInt(1000);
        } while (checkBulletListID(id));

        Bullet copy = new Bullet(id);
        copy.setText(b.getText());
        copy.setVisible(b.getVisible());

        // add new element to bullets
        bullets.add(copy);
        return id;
    }

    /**
     * Removes the list item that matches the id.
     *
     * @param id the ID used to find the Bullet to remove.
     */
    public void removeBullet(String id) {
        bullets.removeIf(b -> b.getID().equals(id));
    }

    /**
     * Returns true if the id is found in the bullets list, false if not found.
     *
     * @param id the string to see if it is equal to any id's in list.
     *
     * @return true if found.
     */
    public boolean checkBulletListID(String id) {
        return getBulletByID(id) != null;
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
        if (this.bullets == null) {
            this.bullets = new LinkedList<>();
        }

        return template.getExperienceTemplate()
            .replaceVariable("organization", MiscUtils.escapeLaTeX(this.organization))
            .replaceVariable("title", MiscUtils.escapeLaTeX(this.title))
            .replaceVariable("location", MiscUtils.escapeLaTeX(this.location))
            .replaceVariable("date", MiscUtils.escapeLaTeX(this.date))
            .replaceVariable("content", this.bullets.stream()
                .filter(ResumeComponent::getVisible)
                .map(f -> f.formatLaTeXString(template))
                .reduce((a, b) -> a + b)
                .orElse("")
            )
            .toString(() -> LOGGER.info("Generated LaTeX for experience \"" + this.title + ", " + this.organization + "\"."));
    }
}
