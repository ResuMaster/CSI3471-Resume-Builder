package to.us.resume_builder.editorview;

import to.us.resume_builder.resume_components.Resume;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;

/**
 * @author Jacob
 * @author Micah
 */
public class EditorController implements ActionListener {
    private EditorStage stage;
    private EditorMenuBar menuBar;
    private EditorCategorySelector sideList;
    private Resume resume;

    /**
     *
     * @param stage
     * @param menuBar
     * @param sideList
     * @return the created EditorController
     * @throws IllegalArgumentException if any parameter is null
     */
    public static EditorController create(EditorStage stage, EditorMenuBar menuBar, EditorCategorySelector sideList) throws IllegalArgumentException {
        if (stage == null || menuBar == null || sideList == null) {
            throw new IllegalArgumentException();
        }
        EditorController e = new EditorController();
        e.stage = stage;
        e.menuBar = menuBar;
        e.sideList = sideList;
        return e;
    }

    public void registerSideList(EditorCategorySelector sideList) {
        this.sideList = sideList;
        this.sideList.setController(this);
    }

    public void registerStage(EditorStage stage) {
        this.stage = stage;
        this.stage.setController(this);
    }

    public void loadResume(Resume r) {
        this.resume = r;
        // TODO load the given resume
    }

    public void loadCategory(String id) {
        stage.showInEditor(resume.getCategoryByID(id));
    }

    public void removeCategory(String id) {
        sideList.removeCategory(id);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
