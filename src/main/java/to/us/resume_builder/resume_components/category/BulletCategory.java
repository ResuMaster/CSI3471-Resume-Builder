package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.export.ResumeTemplate;
import to.us.resume_builder.resume_components.Bullet;
import to.us.resume_builder.resume_components.IBulletContainer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BulletCategory extends Category implements IBulletContainer {

    /**
     * a List of bullets
     */
    private List<Bullet> bullets;

    /**
     * Creates an instance of BulletCategory with id
     * @param id the ID for this instance of Category
     */
    public BulletCategory(String id) {
        super(id, CategoryType.BULLETS);
        bullets = new LinkedList<>();
    }


    /**
     * Get the current List of Bullets for this instance
     * @return the current Bullet List
     */
    public List<Bullet> getBulletList(){
        return bullets;
    }

    /**
     * Get the bullet component by id
     * @param id String to search for id
     * @return the bullet if found, or null if not found
     */
    public Bullet getBulletByID(String id){
        for (Bullet b : bullets) {
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    /**
     * Create a new bullet for bullets list with a random generated id
     * @return the id created for the new bullet
     */
    public String addBullet(){
        do {
            // generate id with current id in the front
            Random rand = new Random();
            String id = this.id + "." + rand.nextInt(1000);
        } while (checkBulletListID(id));

        // add new element to bullets
        bullets.add(new Bullet(id));
        return id;
    }

    /**
     * Removes the list item that matches the id
     * @param id
     */
    public void removeBullet(String id){
        bullets = bullets.stream().filter(e -> !e.getId().equals(id)).collect(Collectors.toList());
    }

    /**
     * Returns true if the id is found in the bullets list, false if not found
     * @param id the string to see if it is equal to any id's in list
     * @return true if found
     */
    public boolean checkBulletListID(String id){
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
            .replaceVariable("title", this.displayName)
            .replaceVariable("content",
                bullets.stream()
                    .map(f -> f.formatLaTeXString(template))
                    .reduce((a, b) -> a + b)
                    .orElse("")
            )
            .toString();
    }
}
