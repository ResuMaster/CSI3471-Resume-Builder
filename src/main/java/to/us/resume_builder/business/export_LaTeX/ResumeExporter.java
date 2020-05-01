package to.us.resume_builder.business.export_LaTeX;

import to.us.resume_builder.business.ApplicationConfiguration;
import to.us.resume_builder.business.server_connect.PDFFacade;
import to.us.resume_builder.business.server_connect.fileio_response.FileIOResponse;
import to.us.resume_builder.business.server_connect.request.FailedRequestException;
import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.ResumeComponent;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * This class handles the exporting of a resume file to a PDF.
 *
 * @author Matthew McCaskill
 * @see Resume
 * @see ResumeTemplate
 */
public class ResumeExporter {
    /**
     * Logs exports and uploads of a file to JSON and PDF
     */
    private static final Logger LOG = Logger.getLogger(ResumeExporter.class.getName());

    /**
     *  The Resume to be exported
     */
    private Resume resume;

    /**
     * Construct a ResumeExporter from a resume.
     *
     * @param resume The resume to export.
     */
    public ResumeExporter(Resume resume) {
        if (resume == null) {
            throw new NullPointerException();
        }

        this.resume = resume;
    }

    /**
     * Export the resume to the specified file.
     *
     * @param exportLocation The name of the file to export to.
     *
     * @return Whether or not the export was successful.
     */
    public boolean export(Path exportLocation) {
        return export(exportLocation, ResumeTemplate.DEFAULT);
    }

    /**
     * Export the resume to the specified exportLocation, using the specified
     * template.
     *
     * @param exportLocation The exportLocation to export to.
     * @param template       The template to use to export the resume.
     *
     * @return Whether or not the export was successful.
     */
    public boolean export(Path exportLocation, ResumeTemplate template) {
        boolean status = true;

        byte[] bytes = null;
        try {
            bytes = PDFFacade.getPDFHandle().getPDF(getLaTeXString(template));
        } catch (InterruptedException | FailedRequestException | IOException e) {
            LOG.warning("Request to get PDF failed: " + e);
            status = false;
        }

        try {
            if (bytes != null) {
                Files.write(exportLocation, bytes);
                LOG.info("PDF export successful");
            }
        } catch (IOException e) {
            LOG.warning("Could not write returned PDF to " + exportLocation + ": " + e);
            status = false;
        }

        if (status) {
            LOG.info("Export process complete.");
        }
        return status;
    }

    /**
     * Sends the {@link to.us.resume_builder.business.export_LaTeX.ResumeExporter#resume}
     * to be exported to PDF and uploaded to file.io, with the response returned
     * here.
     *
     * @return The response from https://file.io
     * @throws InterruptedException The upload request was interrupted
     * @throws FailedRequestException The response returned with an error
     * @throws IOException The request failed to send or the response couldn't be received.
     */
    public FileIOResponse uploadPDF() throws InterruptedException, FailedRequestException, IOException {
        return uploadPDF(ResumeTemplate.DEFAULT);
    }

    /**
     * Sends the {@link to.us.resume_builder.business.export_LaTeX.ResumeExporter#resume}
     * to be exported to PDF and uploaded to file.io, with the response returned
     * here. Uses the specified ResumeTemplate to generate the LaTeX string.
     *
     * @param template the template to use when uploading the {@link Resume} to a PDF
     * @return The response from https://file.io
     * @throws InterruptedException The upload request was interrupted
     * @throws FailedRequestException The response returned with an error
     * @throws IOException The request failed to send or the response couldn't be received.
     */
    public FileIOResponse uploadPDF(ResumeTemplate template) throws InterruptedException, FailedRequestException, IOException {
        return PDFFacade.getPDFHandle().uploadPDF(getLaTeXString(template));
    }

    /**
     * Get the full LaTeX string which represents the resume.
     *
     * @param template The template to use to generate the string.
     *
     * @return The LaTeX representation of this resume.
     */
    public String getLaTeXString(ResumeTemplate template) {
        // Create a StringJoiner to hold the contents of the template
        StringJoiner contents = new StringJoiner(template.getSeparatorTemplate().toString());

        // Get the LaTeX for each of the categories
        resume.getCategoryList().stream()
            .filter(ResumeComponent::getVisible)
            .forEach(c -> contents.add(c.formatLaTeXString(template)));

        // Replace the <content> tag in the resume template with the actual contents
        return template.getLatexTemplate()
            .replaceVariable("content", contents.toString())
            .toString(() -> LOG.info("Generated LaTeX document."));
    }

    /**
     * Compile the resume PDF from an existing <code>.tex</code> source.
     *
     * @param filePath The path to the <code>.tex</code> file to compile.
     *
     * @return Whether or not the compilation was successful.
     * @throws IOException Thrown if an I/O error occurs.
     */
    private boolean compileResumePDF(Path filePath) throws IOException {
        LOG.info("Beginning resume compilation...");

        // Temporary artifacts
        final String[] ARTIFACTS_TO_DELETE = { "aux", "log", "tex" };
        boolean status = true;

        // Attempt to generate the resume
        try {
            ProcessBuilder builder = new ProcessBuilder("pdflatex", "\"" + filePath.toAbsolutePath().toString() + "\"");
            builder.directory(filePath.getParent().toFile());
            // TODO: add dedicated log file
            builder.redirectOutput(new File("./export.log"));
            builder.redirectError(new File("./export.log"));
            LOG.info("PDF compilation log can be found at " + Path.of("./export.log").toAbsolutePath().toString());

            // Run the process
            LOG.info("Attempting to run process \"" + builder.command() + "\"...");
            Process p = builder.start();
            if (!p.waitFor(ApplicationConfiguration.getInstance().getLong("export.timeout"), TimeUnit.SECONDS)) {
                p.destroy();
                LOG.warning("PDF compilation timed out.");
                JOptionPane.showMessageDialog(null, "Resume exporter took too long. Contact IT for additional assistance.");
                status = false;
            }

            // Clean up artifacts
            for (File f : Objects.requireNonNull(filePath.getParent().toFile().listFiles())) {
                if (f.isFile() && Arrays.stream(ARTIFACTS_TO_DELETE).anyMatch(e -> f.getName().endsWith(e))) {
                    LOG.info("Attempting to delete artifact \"" + f.getAbsolutePath() + "\", if it exists.");
                    Files.deleteIfExists(f.toPath());
                }
            }
        } catch (InterruptedException e) {
            LOG.warning("Compilation process was interrupted. Exiting compilation.");
            status = false;
        }

        return status;
    }
}
