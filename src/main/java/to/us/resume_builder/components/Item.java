package to.us.resume_builder.components;

import to.us.resume_builder.export.ILaTeXConvertable;

import java.util.LinkedList;
import java.util.List;

public class Item  implements ILaTeXConvertable {

    /**
     * The ID for an item.
     */
    final String id;

    boolean visible;

    /**
     * The name of the organization worked for, or the name of the school.
     */
    String organization;

    /**
     * The location where work for organization took place.
     */
    String location;

    /**
     * The date that the user want to display, it is a String and user is responsible to format it
     */
    String date;

    /**
     * The position of the user, this is major for school or job title for work
     */
    String title;

    /**
     * The list of bullets that follow an item.
     */
    List<Field> bullets;


    /**
     * Creates an instance of an item with id
     * @param id
     */
    Item(String id){
        this.id = id;
        bullets = new LinkedList<>();
    }

    /**
     * returns the ID for this instance
     * @return id
     */
    public String getId() {
        return id;
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
     * @param organization
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
    public void addBullet(){
        // generate id
        // add instance of field to bullets list
    }

    // TODO Implement
    public void removeBullet(String id){
        // find the instance of field with matching id
        // remove from bullets list
    }

    public boolean getVisible(){
        return visible;
    }

    void toggleCategoryVisibility(){
        visible = !visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toLaTeXString() {
        return null;
    }
}
