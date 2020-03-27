package to.us.resume_builder.main_window;

import java.awt.BorderLayout;

import javax.swing.*;

import to.us.resume_builder.file.ResumeFile;
import to.us.resume_builder.resume_components.Resume;

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

    /** Button to add a new category into the resume. */
    private EditorAddCategoryButton addButton;

    /**
     * Constructs the EditorFrame from a given ResumeFile.
     *
     * @param r The ResumeFile to construct the EditorFrame from.
     */
    public EditorFrame(ResumeFile r) {
        super("Test");

        Resume resume = r.getResume();

        // Create editor components
        menuBar = new EditorMenuBar();
        sideList = new EditorCategorySelector(resume);
        addButton = new EditorAddCategoryButton();
        stage = new EditorStage(resume.getCategoryList().get(0));

        // Create and connect controllers
        EditorController editorController = EditorController.create(stage, sideList, resume);
        MenuController menuController = new MenuController(r);
        addButton.setController(editorController);
        menuBar.setController(menuController);

        // Create move button panel
        // This block is by Ashley Lu Couch
        JPanel moveButtons = new JPanel();
        moveButtons.setLayout(new BoxLayout(moveButtons, BoxLayout.LINE_AXIS));
        JButton upButton = new JButton();
        upButton.addActionListener(e -> {
            sideList.moveCategoryUp();
        });
        JButton downButton = new JButton();
        downButton.addActionListener(e -> {
            sideList.moveCategoryDown();
        });
        moveButtons.add(upButton);
        moveButtons.add(downButton);

        // Assemble side panel
        JPanel selectorPanel = new JPanel(new BorderLayout());
        selectorPanel.add(moveButtons, BorderLayout.NORTH);
        selectorPanel.add(sideList, BorderLayout.CENTER);
        selectorPanel.add(addButton, BorderLayout.SOUTH);

        // Assemble main UI
        setLayout(new BorderLayout());
        setJMenuBar(menuBar);
        add(selectorPanel, BorderLayout.WEST);
        add(stage, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setVisible(true);
    }
}
