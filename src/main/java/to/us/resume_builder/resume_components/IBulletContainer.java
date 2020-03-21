package to.us.resume_builder.resume_components;

import java.util.List;

public interface IBulletContainer {

    /**
     * Get the current List of Bullets for this instance.
     *
     * @return the current Bullet List.
     */
    List<Bullet> getBulletList();

    /**
     * Get the bullet component by id.
     *
     * @param id The String to search for id.
     *
     * @return the bullet if found, or null if not found.
     */
    Bullet getBulletByID(String id);

    /**
     * Create a new bullet for bullets list with a random generated id
     *
     * @return the id created for the new bullet
     */
    String addBullet();

    /**
     * Copy a new bullet for bullets list with a random generated id
     *
     * @return the id created for the new bullet
     */
    String addBullet(Bullet b);

    /**
     * Removes the list item that matches the id.
     *
     * @param id The String to search for id.
     */
    void removeBullet(String id);

    /**
     * Returns true if the id is found in the bullets list, false if not found.
     *
     * @param id The string to see if it is equal to any id's in list.
     *
     * @return true if found.
     */
    boolean checkBulletListID(String id);

    /**
     * Set the number of columns in the bullet list.
     * @param number The value to set columns to.
     */
    void setColumn(int number);

    /**
     * Get the number of columns in the bullet list.
     * @return The number of columns.
     */
    int getColumn();


}
