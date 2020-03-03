package to.us.resume_builder.resume_components;

import to.us.resume_builder.export.ILaTeXConvertable;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Experience extends ResumeComponent implements ILaTeXConvertable, IBulletContainer {
    /**
     * The name of the organization worked for, or the name of the school.
     */
    private String organization;

    /**
     * The location where work for organization took place.
     */
    private String location;

    /**
     * The date that the user want to display, it is a String and user is
     * responsible to format it.
     */
    private String date;

    /**
     * The position of the user, this is major for school or job title for
     * work.
     */
    private String title;

    /**
     * The list of bullets that follow an item.
     */
    private List<Bullet> bullets;


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
        do {
            // generate id with current id in the front
            Random rand = new Random();
            id = this.id + "." + rand.nextInt(1000);
        } while (checkBulletListID(id));

        // add new element to bullets
        bullets.add(new Bullet(id));
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

    @Override
    public String toLaTeXString() {
        return null;
    }
}
