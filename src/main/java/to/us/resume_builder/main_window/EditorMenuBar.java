package to.us.resume_builder.main_window;

import javax.swing.*;

import to.us.resume_builder.util.FileDialog;

/**
 * The menu bar of the main editor window, which has options for exporting
 * the user's data file, or a PDF of their resume.
 *
 * @author Jacob Curtis
 * @author Micah Schiewe
 */
public class EditorMenuBar extends JMenuBar {
    /**
     * The 'File' menu dropdown in the menu bar
     */
    private JMenu file;

    /**
     * Menu item allowing you to export your Resume Data File.
     */
    private JMenuItem exportDataFile;

    /**
     * Menu item allowing you to export your Resume to PDF>
     */
    private JMenuItem exportResume;

    /**
     * The controller which allows saving and exporting data.
     */
    private MenuController controller = null;

    /**
     * Constructs the EditorMenuBar with the File menu, with items for
     * exporting the data file, and to PDF.
     */
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

    /**
     * Sets the MenuController that this class will communicate to when
     * the MenuBar has an event.
     *
     * @param controller The MenuController to be communicated with.
     */
    public void setController(MenuController controller) {
        this.controller = controller;
    }

}
