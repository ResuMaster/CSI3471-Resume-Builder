package to.us.resume_builder.resume_components.category;


import to.us.resume_builder.export.ResumeTemplate;

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
     *
     * @param id the ID for this instance of Category
     */
    public HeaderCategory(String id) {
        super(id, CategoryType.HEADER);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String formatLaTeXString(ResumeTemplate template) {
        return template.getCategoryTemplate(this.type)
            .replaceVariable("name", this.displayName)
            .replaceVariable("address", this.address)
            .replaceVariable("phone", this.phone_number)
            .replaceVariable("link", this.link)
            .replaceVariable("email", this.email)
            .toString();
    }
}
