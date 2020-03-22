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

}
