package to.us.resume_builder.editorview;

import to.us.resume_builder.resume_components.Resume;
import to.us.resume_builder.resume_components.category.TextCategory;

import javax.swing.*;
import java.awt.*;

public class EditorFrame extends JFrame {
    private EditorStage stage;
    private EditorMenuBar menuBar;
    private EditorCategorySelector sideList;
    private Resume resume;

    public EditorFrame(Resume r) {
        super("Test");

        this.resume = r;
        menuBar = new EditorMenuBar();
        sideList = new EditorCategorySelector(r);
        stage = new EditorStage(r.getCategoryList().get(0));

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
