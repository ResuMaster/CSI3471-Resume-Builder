package to.us.resume_builder.business.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import to.us.resume_builder.business.export_LaTeX.ResumeExporter;
import to.us.resume_builder.business.export_LaTeX.ResumeTemplate;
import to.us.resume_builder.business.resume_file_management.ResumeFile;
import to.us.resume_builder.business.resume_file_management.ResumeFileManager;
import to.us.resume_builder.business.server_connect.PDFFacade;
import to.us.resume_builder.business.server_connect.fileio_response.FileIOResponse;
import to.us.resume_builder.business.server_connect.request.FailedRequestException;
import to.us.resume_builder.presentation.EditorMenuBar;

/**
 * The controller for the {@link EditorMenuBar}. This handles actions such as
 * saving and exporting the {@link ResumeFile}.
 * 
 * @author Jacob Curtis
 * @author Micah Schiewe
 */
public class MenuController {
    private static final Logger LOG = Logger.getLogger(MenuController.class.getName());

    /** The resume currently loaded into the editor. */
    private ResumeFile resume;

    /**
     * Constructs a MenuController capable of saving the specified resume.
     * 
     * @param r The resume currently open in the editor this MenuController works
     *          alongside.
     */
    public MenuController(ResumeFile r) {
        this.resume = r;
    }

    /**
     * Saves the resume as a JSON file, which the program can then re-load to
     * continue editing later.
     * 
     * @param path The directory to save the
     */
    public void saveData(String path) {
        LOG.info("Saving data file to " + path);
        try {
            ResumeFileManager.exportFile(resume, path);
            JOptionPane.showMessageDialog(null, "Export successful!");
        } catch (IOException e) {
            LOG.warning("Could not save data file: " + e);
            JOptionPane.showMessageDialog(null, "Export failed; please contact IT for assistance.");
        }
    }

    /**
     * Overwrites the JSON data from which
     * {@link to.us.resume_builder.business.controllers.MenuController#resume resume} was
     * loaded with the current, modified version.
     */
    public void saveData() {
        saveData(resume.getFilePath());
    }

    /**
     * Exports the {@link to.us.resume_builder.business.controllers.MenuController#resume
     * resume} as a formatted pdf, ready to upload to a job.
     * 
     * @param path The location to save the pdf to.
     *
     * @return Whether or not the exporter succeeded.
     */
    public boolean export(Path path) {
        LOG.info("Requesting PDF export");
        ResumeExporter r = new ResumeExporter(resume.getResume());
        byte[] bytes;
        try {
            bytes = PDFFacade.getPDFHandle().getPDF(r.getLaTeXString(ResumeTemplate.DEFAULT));
        } catch (InterruptedException | FailedRequestException | IOException e) {
            LOG.warning("Request to get PDF failed: " + e);
            return false;
        }

        try {
            Files.write(path, bytes);
        } catch (IOException e) {
            LOG.warning("Could not write returned PDF to " + path + ": " + e);
            return false;
        }
        LOG.info("PDF export successful");
        return true;
    }

    /**
     * Sends the {@link to.us.resume_builder.business.controllers.MenuController#resume}
     * to be exported to PDF and uploaded to file.io, with the response returned
     * here.
     *
     * @return The response from https://file.io
     * @throws InterruptedException The upload request was interrupted
     * @throws FailedRequestException The response returned with an error
     * @throws IOException The request failed to send or the response couldn't be received.
     */
    public FileIOResponse uploadPDF() throws InterruptedException, FailedRequestException, IOException {
        LOG.info("Attempting to upload PDF to https://file.io");
        ResumeExporter r = new ResumeExporter(resume.getResume());
        return PDFFacade.getPDFHandle().uploadPDF(r.getLaTeXString(ResumeTemplate.DEFAULT));
    }
}
