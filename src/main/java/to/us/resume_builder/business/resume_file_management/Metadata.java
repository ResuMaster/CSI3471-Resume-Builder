package to.us.resume_builder.business.resume_file_management;

import to.us.resume_builder.unused.user.User;

import java.util.Date;

/**
 * This class contains metadata for a resume, such as the User attached to it
 * and when it was created and last modified.
 *
 * @author Jacob Curtis
 */
public class Metadata {
    /**
     * When the Resume was created
     */
    private Date created;

    /**
     * When the Resume was last changed
     */
    private Date lastModified;

    /**
     * The User's email
     */
    private String email;

    /**
     * Creates a Metadata object with the given parameters.
     *
     * @param user The user attached to the Resume
     * @param created When the Resume was created
     * @param lastModified When the Resume was last modified.
     */
    public Metadata(User user, Date created, Date lastModified) {
        this.email = user.getEmail();
        this.created = created;
        this.lastModified = lastModified;
    }
}
