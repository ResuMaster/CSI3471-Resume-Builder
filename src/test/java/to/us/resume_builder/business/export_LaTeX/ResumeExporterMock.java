package to.us.resume_builder.business.export_LaTeX;

import to.us.resume_builder.business.ApplicationConfiguration;
import to.us.resume_builder.business.server_connect.PDFFacade;
import to.us.resume_builder.business.server_connect.fileio_response.FileIOResponse;
import to.us.resume_builder.business.server_connect.request.FailedRequestException;
import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.ResumeComponent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * This class handles the exporting of a resume file to a PDF.
 *
 * @author Matthew McCaskill
 * @author Brooklynn Stone
 * @see Resume
 * @see ResumeTemplate
 */
public class ResumeExporterMock {
    private Resume resume;

    /**
     * Construct a ResumeExporter from a resume.
     *
     * @param resume The resume to export.
     */
    public ResumeExporterMock(Resume resume) {
        if (resume == null) {
            throw new NullPointerException();
        }

        this.resume = resume;
    }

    /**
     * Export the resume to the specified file.
     *
     * @param exportLocation The name of the file to export to.
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
            status = false;
        }

        try {
            if (bytes != null)
                Files.write(exportLocation, bytes);
        } catch (IOException e) {
            status = false;
        }

        return status;
    }

    /**
     * Sends the {@link to.us.resume_builder.business.export_LaTeX.ResumeExporter}
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
     * Sends the {@link to.us.resume_builder.business.export_LaTeX.ResumeExporter}
     * to be exported to PDF and uploaded to file.io, with the response returned
     * here. Uses the specified ResumeTemplate to generate the LaTeX string.
     *
     * @param template the {@link ResumeTemplate} to use when uploading the {@link Resume} to a PDF
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
            .toString();
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

        // Temporary artifacts
        final String[] ARTIFACTS_TO_DELETE = { "aux", "log", "tex" };
        boolean status = true;

        // Attempt to generate the resume
        try {
            ProcessBuilder builder = new ProcessBuilder("pdflatex", "\"" + filePath.toAbsolutePath().toString() + "\"");
            builder.directory(filePath.getParent().toFile());
            builder.redirectOutput(new File("./export.log"));
            builder.redirectError(new File("./export.log"));

            // Run the process
            Process p = builder.start();
            if (!p.waitFor(ApplicationConfiguration.getInstance().getLong("export.timeout"), TimeUnit.SECONDS)) {
                p.destroy();
                status = false;
            }

            // Clean up artifacts
            for (File f : Objects.requireNonNull(filePath.getParent().toFile().listFiles())) {
                if (f.isFile() && Arrays.stream(ARTIFACTS_TO_DELETE).anyMatch(e -> f.getName().endsWith(e))) {
                    Files.deleteIfExists(f.toPath());
                }
            }
        } catch (InterruptedException e) {
            status = false;
        }

        return status;
    }
}
