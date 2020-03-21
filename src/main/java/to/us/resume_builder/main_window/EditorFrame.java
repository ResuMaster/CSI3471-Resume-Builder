package to.us.resume_builder.main_window;

import to.us.resume_builder.file.ResumeFile;
import to.us.resume_builder.resume_components.Resume;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jacob
 * @author Micah
 */
public class EditorFrame extends JFrame {
    private EditorStage stage;
    private EditorMenuBar menuBar;
    private EditorCategorySelector sideList;

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

    public void registerControllers(EditorController e, MenuController m) {
        menuBar.setController(m);
        e.registerSideList(sideList);
        e.registerStage(stage);
    }

}
