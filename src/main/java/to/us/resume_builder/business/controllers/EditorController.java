package to.us.resume_builder.business.controllers;

import to.us.resume_builder.presentation.EditorCategorySelector;
import to.us.resume_builder.presentation.EditorStage;
import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.category.Category;
import to.us.resume_builder.data.resume_components.category.CategoryType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The main controller for the editor interface. Connects the
 * {@link EditorCategorySelector} and the {@link EditorStage} together and to
 * the {@link Resume} currently under edit.
 * 
 * @author Jacob Curtis
 * @author Micah Schiewe
 */
public class EditorController {
    /** The main UI editing the {@link EditorController#resume resume} */
    private EditorStage stage;

    /**
     * The UI allowing selection of a new {@link Category} to edit in the
     * {@link EditorController#stage stage}
     */
    private EditorCategorySelector sideList;

    /** The resume currently under edit. */
    private Resume resume;

    /**
     * Constructs an EditorController linking the given components together.
     * 
     * @param stage    The main editor connected to the resultant controller
     * @param sideList The list of categories connected to the resultant controller
     * @param resume   The resume initially loaded into the editor
     * @return the created EditorController
     * @throws IllegalArgumentException if any parameter is null
     */
    public static EditorController create(EditorStage stage, EditorCategorySelector sideList, Resume resume)
            throws IllegalArgumentException {
        if (stage == null || sideList == null || resume == null) {
            throw new IllegalArgumentException();
        }
        EditorController e = new EditorController();
        e.registerSideList(sideList);
        e.registerStage(stage);
        e.loadResume(resume);

        return e;
    }

    /**
     * Loads a new {@link Resume} into the editor.
     * 
     * @param r The new resume to edit.
     */
    public void loadResume(Resume r) {
        this.resume = r;
        // TODO load the given resume
    }

    /**
     * Forwards a command to the {@link EditorStage} to show a category in the
     * editor.
     * 
     * @param id The ID of the new category to select.
     */
    public void loadCategory(String id) {
        if (!stage.showInEditor(resume.getCategoryByID(id)))
            sideList.setFocus(stage.getEditID());
    }

    /**
     * Forwards a command to the {@link EditorController#sideList side list} that
     * the category by the listed ID has been removed.
     * 
     * @param id The specific ID of the category to remove.
     */
    public void removeCategory(String id) {
        sideList.removeCategory(id);
    }

    /**
     * Adds a category of the specified type to the resume, updating the UI to
     * acknowledge this change.
     * 
     * @param type The type of category to add to the resume.
     */
    public void addCategory(CategoryType type) {
        String catID = resume.createCategory(type);
        Category newCat = resume.getCategoryByID(catID);

        // Initialize category
        newCat.setName("New " + type.toString());
        newCat.setDisplayName(type.toString());

        // Register and display category
        sideList.addCategory(newCat);
        sideList.setFocus(catID);
        stage.showInEditor(newCat);
    }

    /**
     * Links a new {@link EditorCategorySelector} with the
     * {@link EditorController#stage current stage}.
     * 
     * @param sideList The new category selector
     */
    private void registerSideList(EditorCategorySelector sideList) {
        this.sideList = sideList;
        this.sideList.setController(this);
    }

    /**
     * Links a new {@link EditorStage} with the {@link EditorController#sideList
     * current category selector}.
     * 
     * @param stage The new editor
     */
    private void registerStage(EditorStage stage) {
        this.stage = stage;
        this.stage.setController(this);
    }

    /**
     * Repaint the side list.
     */
    public void repaintSideList() {
        sideList.repaint();
    }

    /**
     * Saves the current category order in the {@link Resume}.
     */
    public void saveCurrentCategoryOrder() {
        List<Category> categories = new LinkedList<>();
        var model = sideList.getModel();

        for (int i = 0; i < model.getSize(); i++) {
            categories.add(model.getElementAt(i));
        }

        resume.setCategoryList(categories);
    }
}

