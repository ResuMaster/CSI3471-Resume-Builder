package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.resume_components.Bullet;
import to.us.resume_builder.resume_components.IBulletContainer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
        return bullets.stream()
                .filter(c -> c.getID().equals(id))
                .findFirst()
                .orElse(null);
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
        bullets.removeIf(b -> b.getID().equals(id));
    }

    /**
     * Returns true if the id is found in the bullets list, false if not found
     * @param id the string to see if it is equal to any id's in list
     * @return true if found
     */
    public boolean checkBulletListID(String id){
        return getBulletByID(id) != null;
    }

    @Override
    public String toLaTeXString() {
        return null;
    }
}
