package to.us.resume_builder.presentation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.moznion.uribuildertiny.URIBuilderTiny;
import to.us.resume_builder.business.controllers.MenuController;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The menu bar of the main editor window, which has options for exporting the
 * user's data file, or a PDF of their resume.
 *
 * @author Jacob Curtis
 * @author Micah Schiewe
 */
public class EditorMenuBar extends JMenuBar {
    /**
     * The controller which allows saving and exporting data.
     */
    private MenuController controller = null;

    /**
     * Constructs the EditorMenuBar with the File menu, with items for exporting
     * the data file, and to PDF.
     */
    public EditorMenuBar() {
        JMenu file = new JMenu("Export");
        JMenuItem exportDataFile;
        file.add(exportDataFile = new JMenuItem("Export Data File"));
        JMenuItem exportResume;
        file.add(exportResume = new JMenuItem("Export Resume"));

        exportDataFile.addActionListener(e -> {
            // Create the file chooser
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop") {
                @Override
                public void approveSelection() {
                    File f = getSelectedFile();
                    if (f.exists() && f.getAbsolutePath().endsWith(".json")) {
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
            fileChooser.setDialogTitle("Export Resume Data");

            // Set file filter
            fileChooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Resume Data File (JSON)", "json");
            fileChooser.resetChoosableFileFilters();
            fileChooser.addChoosableFileFilter(restrict);

            // If "save" was selected, actually save the data file
            if (fileChooser.showDialog(null, "Save Resume Data File") == JFileChooser.APPROVE_OPTION) {
                Path chosenFile = fileChooser.getSelectedFile().toPath();

                // Add .json if not already exists
                if (!chosenFile.toAbsolutePath().toString().endsWith(".json")) {
                    chosenFile = chosenFile.resolveSibling(chosenFile.getFileName() + ".json");
                }

                // Export Resume Data File
                controller.saveData(chosenFile.toString());
            }
        });

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

        JMenu review = new JMenu("Review");
        JMenuItem sendReviewEmail;
        review.add(sendReviewEmail = new JMenuItem("Send Review Email"));

        sendReviewEmail.addActionListener(e -> {
            JTextPane textArea = new JTextPane();

            // TODO: get URLs
            String pdfURL = "PDF URL";
            String jsonURL = "JSON URL";

            String body = "Hello,\n" +
                "\n" +
                "I am currently working on my resume using ResuMaster and would like you to take a look. Click the links below to view and make comments on my resume:\n" +
                "\n" +
                "PDF: " + pdfURL + "\n" +
                "\n" +
                "Data file: " + jsonURL + "\n" +
                "\n" +
                "If you do not have ResuMaster, you can get it here: http://resume-builder.us.to/\n" +
                "\n" +
                "Thanks!";

            Desktop d;
            if (Desktop.isDesktopSupported() && (d = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
                try {
                    URI mailto = new URIBuilderTiny()
                        .addQueryParameter("subject", "Please review my resume!")
                        .addQueryParameter("body", body)
                        .build();
//                    System.out.println(mailto.toString());
                    d.mail(URI.create("mailto:"+mailto.toString().replace("+", "%20")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Could not open email client.");
                }
            }
        });

        add(review);

        revalidate();
    }

    /**
     * Sets the MenuController that this class will communicate to when the
     * MenuBar has an event.
     *
     * @param controller The MenuController to be communicated with.
     */
    public void setController(MenuController controller) {
        this.controller = controller;
    }

}
