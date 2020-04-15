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

/**
 * This class handles the exporting of a resume file to a PDF.
 *
 * @author Matthew McCaskill
 * @see Resume
 * @see ResumeTemplate
 */
public class ResumeExporter {
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
        // Generate the LaTeX code
        String latexCode;
        latexCode = getLaTeXString(template);

        // Get export location
        Path latexPath = Path.of(ApplicationConfiguration.getInstance().getString("export.tempLocation"), MiscUtils.randomAlphanumericString(16) + ".tex");

        // Generate the temp folder
        if (!Files.exists(latexPath.getParent())) {
            Files.createDirectory(latexPath.getParent());
        }

        // Generate the LaTeX file
        Files.writeString(latexPath, latexCode, StandardCharsets.UTF_8, StandardOpenOption.CREATE);

        // Generate the PDF
        boolean status = compileResumePDF(latexPath);

        // Save the pdf to the specified location
        if (status) {
            Files.move(latexPath.resolveSibling(latexPath.getFileName().toString().split("\\.")[0] + ".pdf"), exportLocation, StandardCopyOption.REPLACE_EXISTING);
        } else {
            Files.deleteIfExists(latexPath.resolveSibling(latexPath.getFileName().toString().split("\\.")[0] + ".pdf"));
        }

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
            // TODO: add dedicated log file
            builder.redirectOutput(new File("./export.log"));
            builder.redirectError(new File("./export.log"));

            // Run the process
            Process p = builder.start();
            if (!p.waitFor(ApplicationConfiguration.getInstance().getLong("export.timeout"), TimeUnit.SECONDS)) {
                p.destroy();
                JOptionPane.showMessageDialog(null, "Resume exporter took too long. Contact IT for additional assistance.");
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