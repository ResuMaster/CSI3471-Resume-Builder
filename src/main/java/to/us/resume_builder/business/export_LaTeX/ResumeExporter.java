package to.us.resume_builder.business.export_LaTeX;

import to.us.resume_builder.business.ApplicationConfiguration;
import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.ResumeComponent;
import to.us.resume_builder.business.util.MiscUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    private static Logger LOGGER = Logger.getLogger(ResumeExporter.class.getName());

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
     * @throws IOException Thrown if any errors occur during the export
     *                     process.
     */
    public boolean export(Path exportLocation) throws IOException {
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
     * @throws IOException Thrown if any errors occur during the export
     *                     process.
     */
    public boolean export(Path exportLocation, ResumeTemplate template) throws IOException {
        LOGGER.info("Beginning export of resume with template " + template.name());

        // Generate the LaTeX code
        String latexCode;
        latexCode = getLaTeXString(template);

        // Get export location
        Path latexPath = Path.of(ApplicationConfiguration.getInstance().getString("export.tempLocation"), MiscUtils.randomAlphanumericString(16) + ".tex");
        LOGGER.info("Temporary file name selected: " + latexPath.toAbsolutePath().toString());

        // Generate the temp folder
        if (!Files.exists(latexPath.getParent())) {
            Files.createDirectory(latexPath.getParent());
            LOGGER.info("Temporary folder did not exist, so it was created.");
        }

        // Generate the LaTeX file
        Files.writeString(latexPath, latexCode, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        LOGGER.info("LaTeX file created.");

        // Generate the PDF
        boolean status = compileResumePDF(latexPath);

        // Save the pdf to the specified location
        if (status) {
            LOGGER.info("PDF compilation successful.");
            Path finalLocation = latexPath.resolveSibling(latexPath.getFileName().toString().split("\\.")[0] + ".pdf");
            LOGGER.info("Moving generated PDF to " + finalLocation.toAbsolutePath().toString() + "...");
            Files.move(finalLocation, exportLocation, StandardCopyOption.REPLACE_EXISTING);
        } else {
            LOGGER.warning("PDF compilation failed.");
            LOGGER.warning("Deleting temporary PDF, if it exists...");
            Files.deleteIfExists(latexPath.resolveSibling(latexPath.getFileName().toString().split("\\.")[0] + ".pdf"));
        }

        LOGGER.info("Export process complete.");
        return status;
    }

    /**
     * Get the full LaTeX string which represents the resume.
     *
     * @param template The template to use to generate the string.
     *
     * @return The LaTeX representation of this resume.
     */
    private String getLaTeXString(ResumeTemplate template) {
        // Create a StringJoiner to hold the contents of the template
        StringJoiner contents = new StringJoiner(template.getSeparatorTemplate().toString());

        // Get the LaTeX for each of the categories
        resume.getCategoryList().stream()
            .filter(ResumeComponent::getVisible)
            .forEach(c -> contents.add(c.formatLaTeXString(template)));

        // Replace the <content> tag in the resume template with the actual contents
        return template.getLatexTemplate()
            .replaceVariable("content", contents.toString())
            .toString(() -> LOGGER.info("Generated LaTeX document."));
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
        LOGGER.info("Beginning resume compilation...");

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
            LOGGER.info("PDF compilation log can be found at " + Path.of("./export.log").toAbsolutePath().toString());

            // Run the process
            LOGGER.info("Attempting to run process \"" + builder.command() + "\"...");
            Process p = builder.start();
            if (!p.waitFor(ApplicationConfiguration.getInstance().getLong("export.timeout"), TimeUnit.SECONDS)) {
                p.destroy();
                LOGGER.warning("PDF compilation timed out.");
                JOptionPane.showMessageDialog(null, "Resume exporter took too long. Contact IT for additional assistance.");
                status = false;
            }

            // Clean up artifacts
            for (File f : Objects.requireNonNull(filePath.getParent().toFile().listFiles())) {
                if (f.isFile() && Arrays.stream(ARTIFACTS_TO_DELETE).anyMatch(e -> f.getName().endsWith(e))) {
                    LOGGER.info("Attempting to delete artifact \"" + f.getAbsolutePath() + "\", if it exists.");
                    Files.deleteIfExists(f.toPath());
                }
            }
        } catch (InterruptedException e) {
            LOGGER.warning("Compilation process was interrupted. Exiting compilation.");
            status = false;
        }

        return status;
    }
}
