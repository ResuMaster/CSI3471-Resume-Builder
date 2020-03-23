package to.us.resume_builder.main_window;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import to.us.resume_builder.util.FileDialog;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            // Create the file chooser
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop") {
                @Override
                public void approveSelection() {
                    File f = getSelectedFile();
                    if (f.exists() && f.getAbsolutePath().endsWith(".pdf")) {
                        int result = JOptionPane.showConfirmDialog(this, "The file exists, overwrite?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
                        switch (result) {
                            case JOptionPane.YES_OPTION:
                                super.approveSelection();
                                return;
                            case JOptionPane.NO_OPTION:
                            case JOptionPane.CLOSED_OPTION:
                                return;
                            case JOptionPane.CANCEL_OPTION:
                                cancelSelection();
                                return;
                        }
                    }
                    super.approveSelection();
                }
            };

            // Set file chooser options
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("Export Resume PDF");

            // Set file filter
            fileChooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Portable Document Format (PDF)", "pdf");
            fileChooser.resetChoosableFileFilters();
            fileChooser.addChoosableFileFilter(restrict);

            // If "save" was selected, actually export the resume
            if (fileChooser.showDialog(null, "Export PDF") == JFileChooser.APPROVE_OPTION) {
                Path chosenFile = fileChooser.getSelectedFile().toPath();

                // Add .pdf if not already exists
                if (!chosenFile.toAbsolutePath().toString().endsWith(".pdf")) {
                    chosenFile = chosenFile.resolveSibling(chosenFile.getFileName() + ".pdf");
                }

                // Export Resume
                boolean status = controller.export(chosenFile);

                // Open the file if it exists
                if (status && Files.exists(chosenFile)) {
                    if (Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().open(chosenFile.toFile());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
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
