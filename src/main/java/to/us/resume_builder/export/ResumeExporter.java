package to.us.resume_builder.export;

import to.us.resume_builder.ApplicationConfiguration;
import to.us.resume_builder.resume_components.Resume;
import to.us.resume_builder.resume_components.category.Category;
import to.us.resume_builder.util.MiscUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.StringJoiner;

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
        this.resume = resume;
    }

    /**
     * Export the resume to the specified file.
     *
     * @param fileName The name of the file to export to.
     *
     * @return Whether or not the export was successful.
     * @throws IOException Thrown if any errors occur during the export
     *                     process.
     */
    public boolean export(String fileName) throws IOException {
        return export(fileName, ResumeTemplate.DEFAULT);
    }

    /**
     * Export the resume to the specified file, using the specified template.
     *
     * @param fileName The name of the file to export to.
     * @param template The template to use to export the resume.
     *
     * @return Whether or not the export was successful.
     * @throws IOException Thrown if any errors occur during the export
     *                     process.
     */
    public boolean export(String fileName, ResumeTemplate template) throws IOException {
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
        Files.writeString(latexPath, latexCode, StandardOpenOption.CREATE);

        // Generate the PDF
        boolean status = createResumePDF(latexPath);

        // TODO: do something with the resulting pdf
        Files.move(latexPath.resolveSibling(latexPath.getFileName().toString().split("\\.")[0] + ".pdf"), Path.of("export.pdf"), StandardCopyOption.REPLACE_EXISTING);

        return status;
    }

    private String getLaTeXString(ResumeTemplate template) {
        StringJoiner sb = new StringJoiner(template.getSeparatorTemplate().toString());

        List<Category> categories = resume.getCategoryList();

        categories.forEach(c -> sb.add(c.formatLaTeXString(template)));

        return template.getLatexTemplate()
            .replaceVariable("content", sb.toString())
            .toString();
    }

    private boolean createResumePDF(Path filePath) {
        // Temporary artifacts
        final String[] ARTIFACTS_TO_DELETE = { "aux", "log", "tex" };

        // Attempt to generate
        try {
            Runtime.getRuntime().exec("pdflatex \"" + filePath.toAbsolutePath().toString() + "\"", null, filePath.getParent().toFile()).waitFor();

            // Clean up artifacts
            for (String extension : ARTIFACTS_TO_DELETE) {
                System.out.println(filePath.resolveSibling(filePath.getFileName().toString().split("\\.")[0] + "." + extension));
                Files.deleteIfExists(filePath.resolveSibling(filePath.getFileName().toString().split("\\.")[0] + "." + extension));
            }

            return true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();  // TODO: remove
            return false;
        }
    }
}
