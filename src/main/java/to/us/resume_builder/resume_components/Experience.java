package to.us.resume_builder.resume_components;

import to.us.resume_builder.export.ILaTeXConvertable;
import to.us.resume_builder.export.ResumeTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

// TODO look into this extending BulletCategory
public class Experience extends ResumeComponent  implements ILaTeXConvertable {
    /**
     * The name of the organization worked for, or the name of the school.
     */
    private String organization;

    /**
     * The location where work for organization took place.
     */
    private String location;

    /**
     * The date that the user want to display, it is a String and user is responsible to format it
     */
    private String date;

    /**
     * The position of the user, this is major for school or job title for work
     */
    private String title;

    /**
     * The list of bullets that follow an item.
     */
    private List<Bullet> bullets;


    /**
     * Creates an instance of an item with id
     * @param id ud
     */
    public Experience(String id){
        super(id);
        bullets = new LinkedList<>();
    }

    /**
     * returns the Organization for this instance
     * @return organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * sets organization of this item
     * @param organization ud
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // TODO Implement
    public String addBullet(){
        // generate id with current id in the front
        Random rand = new Random();
        String id = this.id + rand.nextInt(1000);
        // TODO check id to make sure that it is not a duplicate

        // add new element to bullets
        bullets.add(new Bullet(id));
        return id;
    }

    // TODO Implement
    public void removeBullet(String id){
        // find the instance of field with matching id
        // remove from bullets list
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
        return template.getExperienceTemplate()
            .replaceVariable("organization", this.organization)
            .replaceVariable("title", this.title)
            .replaceVariable("location", this.location)
            .replaceVariable("date", this.date)
            .replaceVariable("content", bullets.stream()
                .map(f -> f.formatLaTeXString(template))
                .reduce((a, b) -> a + b)
                .orElse("")
            )
            .toString();
    }
}
