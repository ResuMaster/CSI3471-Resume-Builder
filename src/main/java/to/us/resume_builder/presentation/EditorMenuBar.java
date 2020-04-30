package to.us.resume_builder.presentation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.moznion.uribuildertiny.URIBuilderTiny;
import to.us.resume_builder.business.controllers.MenuController;
import to.us.resume_builder.business.server_connect.fileio_response.FileIOResponse;
import to.us.resume_builder.business.server_connect.request.FailedRequestException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * The menu bar of the main editor window, which has options for exporting the
 * user's data file, or a PDF of their resume.
 *
 * @author Jacob Curtis
 * @author Micah Schiewe
 */
public class EditorMenuBar extends JMenuBar {
    private static final Logger LOG = Logger.getLogger(EditorMenuBar.class.getName());

    /**
     * The controller which allows saving and exporting data.
     */
    private MenuController controller = null;

    /**
     * Constructs the EditorMenuBar with the File menu, with items for exporting
     * the data file, and to PDF.
     */
    public EditorMenuBar() {
        JMenu file = new JMenu("File");
        JMenuItem exportDataFile;
        file.add(exportDataFile = new JMenuItem("Save Resume"));
        JMenuItem exportResume;
        file.add(exportResume = new JMenuItem("Export Resume PDF"));

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
            fileChooser.setDialogTitle("Save Resume Data");

            // Set file filter
            fileChooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Resume Data File (JSON)", "json");
            fileChooser.resetChoosableFileFilters();
            fileChooser.addChoosableFileFilter(restrict);

            // If "save" was selected, actually save the data file
            if (fileChooser.showDialog(null, "Save Resume Data File") == JFileChooser.APPROVE_OPTION) {
                Path chosenFile = fileChooser.getSelectedFile().toPath();
                LOG.info("Attempting to save resume data to " + chosenFile);

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
                            LOG.warning("Could not open PDF: " + ex);
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
            FileIOResponse response = null;
            try {
                LOG.info("Uploading PDF to file.io");
                response = controller.uploadPDF();
            } catch (InterruptedException | FailedRequestException | IOException ex) {
                LOG.warning("Failed to upload PDF: " + ex);
            }

            if (response == null || !response.isSuccess()) {
                LOG.warning("Response is failure.");
                JOptionPane.showMessageDialog(this, "Could not upload PDF. Try again later. If the problem persists, try exporting your resume as a PDF and manually attach it to an email.");
                return;
            }

            String pdfURL = response.getLink();
            String expiry = response.getExpiry();
            String body = "Hello,\n" +
                "\n" +
                "I am currently working on my resume using ResuMaster, and wanted to get some constructive feedback. My current draft can be found at: " + pdfURL + " (link expires after 14 days)\n" +
                "\n" +
                "Thanks!\n" +
                "\n" +
                "Powered by ResuMaster\nhttp://resume-builder.us.to/";

            Desktop d;
            if (Desktop.isDesktopSupported() && (d = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
                try {
                    URI mailto = new URIBuilderTiny()
                        .addQueryParameter("subject", "Please review my resume!")
                        .addQueryParameter("body", body)
                        .build();
//                    System.out.println(mailto.toString());
                    LOG.info("Sending email");
                    d.mail(URI.create("mailto:"+mailto.toString().replace("+", "%20")));
                } catch (IOException ex) {
                    LOG.info("Could not send email: " + ex);
                    JOptionPane.showMessageDialog(this, "Could not open email client.");
                }
            } else {
                LOG.warning("Opening an email is not supported, opening a window with a message instead.");

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
                panel.add(new JLabel("I could not open email client automatically, but here is a pre-generated email body to copy-and-paste:"));

                JTextArea email = new JTextArea(body);
                email.setEditable(false);
                panel.add(email);

                JOptionPane.showMessageDialog(this, panel);
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
