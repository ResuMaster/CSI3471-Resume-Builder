package to.us.resume_builder.editorview;

import to.us.resume_builder.resume_components.Resume;

import javax.swing.*;
import java.awt.*;

public class EditorFrame extends JFrame {
    private EditorStage stage;
    private EditorMenuBar menuBar;
    private EditorCategorySelector sideList;

    public EditorFrame(Resume r) {
        super("Test");

        menuBar = new EditorMenuBar();
        sideList = new EditorCategorySelector(r);
        stage = new EditorStage(r.getCategoryList().get(0));

        EditorController controller = EditorController.create(stage, menuBar, sideList);
        registerController(controller);

        setLayout(new BorderLayout());
        setJMenuBar(menuBar);
        add(sideList, BorderLayout.WEST);
        add(stage, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void registerController(EditorController e) {
        menuBar.setController(e);
        e.registerSideList(sideList);
        e.registerStage(stage);
    }

}
