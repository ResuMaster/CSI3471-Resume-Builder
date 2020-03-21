package to.us.resume_builder.main_window;

import to.us.resume_builder.file.ResumeFile;
import to.us.resume_builder.resume_components.Resume;

import javax.swing.*;
import java.awt.*;

/**
 * The main editor frame which houses the more granular aspects of the
 * Editor.
 *
 * @author Jacob Curtis
 * @author Micah Schiewe
 */
public class EditorFrame extends JFrame {
    /**
     * The stage shows the information stored in each category and allows
     * for modifying the data.
     */
    private EditorStage stage;

    /**
     * Menu bar for exporting the Resume data file and Resume PDF.
     */
    private EditorMenuBar menuBar;

    /**
     * Side list for selecting which category you want to view in the stage.
     */
    private EditorCategorySelector sideList;

    /**
     * Constructs the EditorFrame from a given ResumeFile.
     *
     * @param r The ResumeFile to construct the EditorFrame from.
     */
    public EditorFrame(ResumeFile r) {
        super("Test");

        Resume resume = r.getResume();

        menuBar = new EditorMenuBar();
        sideList = new EditorCategorySelector(resume);
        stage = new EditorStage(resume.getCategoryList().get(0));

        EditorController controller = EditorController.create(stage, sideList, resume);
        MenuController menuController = new MenuController(r);
        registerControllers(controller, menuController);

        setLayout(new BorderLayout());
        setJMenuBar(menuBar);
        add(sideList, BorderLayout.WEST);
        add(stage, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
//        pack();
        setVisible(true);
    }

    /**
     * Register the controller for the menu bar, side list, and stage using
     * the provided controllers.
     *
     * @param e The controller for the Editor
     * @param m The controller for the Menu Bar.
     */
    public void registerControllers(EditorController e, MenuController m) {
        menuBar.setController(m);
        e.registerSideList(sideList);
        e.registerStage(stage);
    }

}
