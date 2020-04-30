package to.us.resume_builder.data.resume_components.category;


import java.util.logging.Logger;

import to.us.resume_builder.business.export_LaTeX.ResumeTemplate;
import to.us.resume_builder.business.util.MiscUtils;
import to.us.resume_builder.data.resume_components.CategoryVisitor;

public class HeaderCategory extends Category {
    private static Logger LOGGER = Logger.getLogger(HeaderCategory.class.getName());

    /**
     * The link that the user want to have in the header, usually linkedin.com.
     */
    private String link = "";

    /**
     * The user's email address that they want on the resume.
     */
    private String email = "";

    /**
     * The user's phone number that they want on the resume.
     */
    private String phoneNumber = "";

    /**
     * The user's address than they want on the resume.
     */
    private String address = "";

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
            .replaceVariable("name", MiscUtils.escapeLaTeX(this.displayName))
            .replaceVariable("address", MiscUtils.escapeLaTeX(this.address))
            .replaceVariable("phone", MiscUtils.escapeLaTeX(this.phoneNumber))
            .replaceVariable("link", MiscUtils.escapeLaTeX(this.link))
            .replaceVariable("email", MiscUtils.escapeLaTeX(this.email))
            .toString(() -> LOGGER.info("Generated LaTeX for header category \"" + this.displayName + "\"."));
    }

    /**
     * Allow a CategoryVisitor to visit this HeaderCategory.
     * 
     * @param v The visitor to this HeaderCategory.
     */
    @Override
    public void accept(CategoryVisitor v) {
        v.visit(this);
    }
}
