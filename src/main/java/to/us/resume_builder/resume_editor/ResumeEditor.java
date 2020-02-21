package to.us.resume_builder.resume_editor;

import to.us.resume_builder.adminView.UpdateUserController;
import to.us.resume_builder.export.ILaTeXConvertable;
import to.us.resume_builder.file.Metadata;
import to.us.resume_builder.file.ResumeFile;
import to.us.resume_builder.file.ResumeFileManager;
import to.us.resume_builder.resume_components.category.*;
import to.us.resume_builder.resume_components.*;
import to.us.resume_builder.user.User;
import to.us.resume_builder.user.UserDBC;
import to.us.resume_builder.user.UserRole;

import java.io.*;


public class ResumeEditor implements ILaTeXConvertable {

    ResumeEditor(User u, User a, Resume r) {
        user = u;
        database = new UserDBC(user);
        resume = r;
        fileManager = new ResumeFileManager();
    }
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
        // TODO implement
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
    public void exportResume(String path) {
        try {
            FileOutputStream file = new FileOutputStream(new File(path));
            file.write(toLaTeXString().getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // email resume
    public void emailResume() {
        // TODO implement
    }


    // import data file
    public void importDataFile(String path) {
        try {
            resume = fileManager.importFile(path).getResume();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // export data file
    public void exportDataFile(String path) {
        try {
            fileManager.exportFile(new ResumeFile(new Metadata(), resume), path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // cancel export data
    public void cancelExportData() {
        // TODO implement
    }


    // init role change
    public void startChangeRole(UserRole role) {
        if(controller.initiateRoleChange(user)) {
            controller.changeUserRole(role);
            user.setRole(role);
        }
    }

//    // set role request
//    public void changeRole(UserRole role) {
//        controller.changeUserRole(role);
//        user.setRole(role);
//    }


    // create account
    public void createAccount(User u) {
        user = u;
    }

    // validate account
    public void validateCredentials() {
        // TODO implement
    }


    // request review
    public void requestReview() {
        // TODO implement
    }

    // set reviewers
    public void setReviewers() {
        // TODO implement
    }

    // submit review
    public void submitReviewRequest() {
        // TODO implement
    }

    // cancel review
    public void cancelReviewRequest() {
        // TODO implement
    }


    // select review resume
    public void selectReviewResume(Resume r) {
        // TODO implement
    }

    // enter comment
    public void addComment() {
        // TODO implement
    }

    // submit comment
    public void submitComment() {
        // TODO implement
    }

    // cancel review
    public void cancelReview() {
        // TODO implement
    }

    // submit review
    public void submitReview() {
        // TODO implement
    }

    // save resume changes
    public void saveChanges() {
        // TODO implement
    }

    @Override
    public String toLaTeXString() {
        return null;
    }

    private static UpdateUserController controller;
    private Resume resume;
    private User user;
    private UserDBC database;
    private ResumeFileManager fileManager;
}
