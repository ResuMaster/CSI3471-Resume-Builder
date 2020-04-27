package to.us.resume_builder.data.resume_components.category;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import to.us.resume_builder.business.export_LaTeX.ResumeTemplate;
import to.us.resume_builder.business.util.MiscUtils;
import to.us.resume_builder.data.resume_components.Bullet;
import to.us.resume_builder.data.resume_components.CategoryVisitor;
import to.us.resume_builder.data.resume_components.IBulletContainer;
import to.us.resume_builder.data.resume_components.ResumeComponent;

public class BulletCategory extends Category implements IBulletContainer {
    private static Logger LOGGER = Logger.getLogger(BulletCategory.class.getName());

    /**
     * A List of bullets.
     */
    private List<Bullet> bullets;

    /**
     * The number of columns for the bullet list
     */
    private int columnCount = 1;

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
    @Override
    public List<Bullet> getBulletList() {
        return bullets;
    }

    /**
     * Set the number of columns in the bullet list.
     *
     * @param number The value to set columns to.
     */
    @Override
    public void setColumn(int number) {
        this.columnCount = number;
    }

    /**
     * Get the number of columns in the bullet list.
     *
     * @return The number of columns.
     */
    @Override
    public int getColumn() {
        return this.columnCount;
    }

    /**
     * Get the bullet component by id.
     *
     * @param id String to search for id.
     *
     * @return The bullet if found, or null if not found.
     */
    @Override
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
    @Override
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
    @Override
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
     * @param id The String to find which instant.
     */
    @Override
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
    @Override
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

        return template.getCategoryTemplate(this.type)
            .replaceVariable("title", MiscUtils.escapeLaTeX(this.displayName))
            .replaceVariable("content",
                bullets.stream()
                    .filter(ResumeComponent::getVisible)
                    .map(f -> f.formatLaTeXString(template))
                    .reduce((a, b) -> a + b)
                    .orElse("")
            )
            .toString(() -> LOGGER.info("Generated LaTeX for bullet category \"" + this.displayName + "\"."));
    }

    /**
     * Allow a CategoryVisitor to visit this BulletCategory.
     * 
     * @param v The visitor to this BulletCategory.
     */
    @Override
    public void accept(CategoryVisitor v) {
        v.visit(this);
    }
}
