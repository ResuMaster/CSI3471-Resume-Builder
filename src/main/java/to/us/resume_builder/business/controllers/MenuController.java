package to.us.resume_builder.business.controllers;

import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JOptionPane;

import to.us.resume_builder.business.export_LaTeX.ResumeExporter;
import to.us.resume_builder.business.resume_file_management.ResumeFile;
import to.us.resume_builder.business.resume_file_management.ResumeFileManager;
import to.us.resume_builder.presentation.EditorMenuBar;

/**
 * The controller for the {@link EditorMenuBar}. This handles actions such as
 * saving and exporting the {@link ResumeFile}.
 * 
 * @author Jacob Curtis
 * @author Micah Schiewe
 */
public class MenuController {
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
        try {
            ResumeFileManager.exportFile(resume, path);
            JOptionPane.showMessageDialog(null, "Export successful!");
        } catch (IOException e) {
            e.printStackTrace();
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
        ResumeExporter r = new ResumeExporter(resume.getResume());
        try {
            return r.export(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
