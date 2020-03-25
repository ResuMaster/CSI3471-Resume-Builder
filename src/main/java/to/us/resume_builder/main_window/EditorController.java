package to.us.resume_builder.main_window;

import to.us.resume_builder.resume_components.Resume;

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
        e.stage = stage;
        e.sideList = sideList;
        e.resume = resume;
        return e;
    }

    /**
     * Links a new {@link EditorCategorySelector} with the
     * {@link EditorController#stage current stage}.
     * 
     * @param sideList The new category selector
     */
    public void registerSideList(EditorCategorySelector sideList) {
        this.sideList = sideList;
        this.sideList.setController(this);
    }

    /**
     * Links a new {@link EditorStage} with the {@link EditorController#sideList
     * current category selector}.
     * 
     * @param stage The new editor
     */
    public void registerStage(EditorStage stage) {
        this.stage = stage;
        this.stage.setController(this);
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
        stage.showInEditor(resume.getCategoryByID(id));
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
}
