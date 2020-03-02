package to.us.resume_builder.resume_components.category;


public class HeaderCategory extends Category {

    /**
     * The link that the user want to have in the header, usually linkedin.com
     */
    private String link;

    /**
     * The user's email address that they want on the resume
     */
    private String email;

    /**
     * The user's phone number that they want on the resume
     */
    private String phone_number;

    /**
     * The user's address than they want on the resume
     */
    private String address;

    /**
     * Creates an instance of HeaderCategory with id
     * @param id the ID for this instance of Category
     */
    public HeaderCategory(String id) {
        super(id, CategoryType.HEADER);
    }

    /**
     * Returns the current String link
     * @return link
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the value of link
     * @param link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Returns the current String email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the current String phone number
     * @return the current String phone_number
     */
    public String getPhoneNumber() {
        return phone_number;
    }

    /**
     * Sets the String phone_number
     * @param phone_number the new String to set to
     */
    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    /**
     * Returns the current String address
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the String address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toLaTeXString() {
        return null;
    }
}
