package to.us.resume_builder.main_window;

import javax.swing.*;

import to.us.resume_builder.util.FileDialog;

/**
 * @author Jacob
 * @author Micah
 */
public class EditorMenuBar extends JMenuBar {
    private JMenu file;
    private JMenuItem exportDataFile;
    private JMenuItem exportResume;
    private MenuController controller = null;

    public void setController(MenuController controller) {
        this.controller = controller;
    }

    public EditorMenuBar() {
        file = new JMenu("File");
        file.add(exportDataFile = new JMenuItem("Export Data File"));
        file.add(exportResume = new JMenuItem("Export Resume"));

        exportDataFile.addActionListener(e -> controller.saveData());
        exportResume.addActionListener(e -> {
            FileDialog f = new FileDialog("pdf", this);
            String path = f.getFile();
            controller.export(path);
        });

        add(file);
        revalidate();
    }

}
