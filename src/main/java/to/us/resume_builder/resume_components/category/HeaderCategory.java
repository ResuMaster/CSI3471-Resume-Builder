package to.us.resume_builder.resume_components.category;


public class HeaderCategory extends Category {

    /**
     * The link that the user want to have in the header, usually linkedin.com.
     */
    private String link;

    /**
     * The user's email address that they want on the resume.
     */
    private String email;

    /**
     * The user's phone number that they want on the resume.
     */
    private String phoneNumber;

    /**
     * The user's address than they want on the resume.
     */
    private String address;

    /**
     * Creates an instance of HeaderCategory with id.
     *
     * @param id The ID for this instance of Category.
     */
    public HeaderCategory(String id) {
        super(id, CategoryType.HEADER);
    }

    /**
     * Returns the current String link for this instance.
     *
     * @return The String for link.
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the value of link for this instance.
     *
     * @param link The String to set link to.
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Returns the current String email for this instance.
     *
     * @return The String for email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of email in this instance.
     *
     * @param email The String to set email to.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the current String phoneNumber for this instance.
     *
     * @return The String phoneNumber.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the String phoneNumber for this instance.
     *
     * @param phoneNumber The new String to set phoneNumber to.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the current String address for this instance.
     *
     * @return The String address for.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the String address for this instance.
     *
     * @param address The String to set address to.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toLaTeXString() {
        return null;
    }
}
