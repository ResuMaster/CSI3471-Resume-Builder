package to.us.resume_builder.unused.user;

/**
 * This class indirects connecting to the database to handle user management.
 * Currently a dummy class since IDK [sic] what kind of database we'll use.
 * 
 * @author Micah
 */
public class UserDBC {

    // The user this object connects to the database.
    private User connectedUser;

    /**
     * Creates a connection to the database using the specified user.
     * <p>
     * May want to replace with a factory method to allow a different DBC to be
     * created for Writers--to access their Resume.
     * 
     * @param user The User to connect to the database.
     */
    public UserDBC(User user) {
        connectedUser = user;
    }

    /**
     * Updates the specified user's role.
     * 
     * @param newRole The User who's data is to change.
     * @return Whether or not the update succeeded.
     */
    public boolean changeRole(UserRole newRole) {
        connectedUser.setRole(newRole);
        updateUserInDB();
        return true;
    }

    /**
     * Get the UserRole associated with the given User in the database.
     * 
     * @return the UserRole for the specified user, if the username and password
     *         exist.
     */
    public UserRole getRole() {
        return UserRole.ADMIN;
    }

    /**
     * Connects a different user to the database.
     * 
     * @param newUser The new user to access the database data for.
     */
    public void changeUser(User newUser) {
        connectedUser = newUser;
    }

    /**
     * Pushes any changes in currentUser to the database.
     * <p>
     * Prevents duplication of database-accessing code for each different parameter.
     */
    private void updateUserInDB() {
        // TODO implement our DB-esque file system.
    }
}
