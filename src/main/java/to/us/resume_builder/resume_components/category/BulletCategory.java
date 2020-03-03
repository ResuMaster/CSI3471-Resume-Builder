package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.export.ResumeTemplate;
import to.us.resume_builder.resume_components.Bullet;
import to.us.resume_builder.resume_components.IBulletContainer;
import to.us.resume_builder.util.MiscUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BulletCategory extends Category implements IBulletContainer {

    /**
     * A List of bullets.
     */
    private List<Bullet> bullets;

    /**
     * The number of columns for the bullet list
     */
    private int columnCount;

    /**
     * Creates an instance of BulletCategory with id.
     *
     * @param id The ID for this instance of Category.
     */
    public BulletCategory(String id) {
        super(id, CategoryType.BULLETS);
        bullets = new LinkedList<>();
    }


    /**
     * Get the current List of Bullets for this instance.
     *
     * @return the current Bullet List.
     */
    public List<Bullet> getBulletList() {
        return bullets;
    }

    /**
     * Set the number of columns in the bullet list.
     * @param number The value to set columns to.
     */
    public void setColumn(int number){
        this.columnCount = number;
    }

    /**
     * Get the number of columns in the bullet list.
     * @return The number of columns.
     */
    public int getColumn(){
        return this.columnCount;
    }

    /**
     * Get the bullet component by id.
     *
     * @param id String to search for id.
     *
     * @return The bullet if found, or null if not found.
     */
    public Bullet getBulletByID(String id) {
        return bullets.stream()
            .filter(c -> c.getID().equals(id))
            .findFirst()
            .orElse(null);
    }

    /**
     * Create a new bullet for bullets list with a random generated id.
     *
     * @return The id created for the new bullet.
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
     * @param id The String to find which instant.
     */
    public void removeBullet(String id) {
        bullets.removeIf(b -> b.getID().equals(id));
    }

    /**
     * Returns true if the id is found in the bullets list, false if not found.
     *
     * @param id The string to see if it is equal to any ID's in list.
     *
     * @return True if found.
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
        return template.getCategoryTemplate(this.type)
            .replaceVariable("title", MiscUtils.escapeLaTeX(this.displayName))
            .replaceVariable("content",
                bullets.stream()
                    .map(f -> f.formatLaTeXString(template))
                    .reduce((a, b) -> a + b)
                    .orElse("")
            )
            .toString();
    }
}
