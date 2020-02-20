package to.us.resume_builder.resume_editor;

import to.us.resume_builder.resume_components.category.*;
import to.us.resume_builder.resume_components.*;


public class ResumeEditor {
    /**
     * When the Writer decides to add a Category, we need to take that Category and add it to the Resume
     * @param c the Category being added to the Resume
     */
    public void addCategory(CategoryType c) {
        resume.createCategory(c);
    }

    // add experience
    public void addExperience(ExperienceCategory c) {
        c.addExperience();
    }

    // add bullet
    public void addBullet(BulletCategory c) {
        c.addBullet();
    }

    // add header
    public void addHeader(HeaderCategory c) {
        resume.createCategory(CategoryType.HEADER);
    }

    // change category or category type
    public void changeCategory(Category c) {
        // TODO implement
    }

    // change experience
    public void changeExperience(ExperienceCategory c) {
        // TODO implement
    }

    // change bullet
    public void changeBullet(BulletCategory c, Field f) {
        // TODO implement
    }

    // change header
    public void changeHeader(HeaderCategory c) {
        // TODO implement
    }


    // remove category
    public void removeCategory(Category c) {
        // TODO implement
    }

    // remove experience
    public void removeExperience(ExperienceCategory c, Experience e) {
        c.removeExperience(e.getId());
    }

    // remove bullet
    public void removeBullet(BulletCategory c, Field f) {
        c.removeBullet(f.getId());
    }

    // remove header
    public void removeHeader(HeaderCategory c) {

    }

    // set toggle field
    public void toggleField(Field f) {
        f.setVisible(!f.getVisible());
    }

    // set toggle experience
    public void toggleExperience(Experience e) {
        e.setVisible(!e.getVisible());
    }

    // set toggle category
    public void toggleCategory(Category c) {
        c.setVisible(!c.getVisible());
    }

    // set toggle header
    public void toggleHeader(HeaderCategory c) {
        c.setVisible(!c.getVisible());
    }

    // preview resume
    public void previewResume() {
        // TODO implement
    }


    // export resume
    public void exportResume() {
        // TODO implement
    }

    // cancel export resume
    public void cancelExport() {
        // TODO implement
    }

    // set save location
    public void saveLocation() {
        // TODO implement
    }


    // email resume
    public void emailResume() {
        // TODO implement
    }


    // import data file
    public void importDataFile() {
        // TODO implement
    }

    // export data file

    // cancel export data


    // init role change

    // set role request


    // create account

    // validate account


    // request review

    // set reviewers

    // submit review

    // cancel review


    // select review resume

    // enter comment

    // submit comment

    // cancel review

    // submit review


    // save resume changes


    // select comment field

    // select comment header

    // select comment category

    // select comment experience


    //

    private Resume resume;
}
