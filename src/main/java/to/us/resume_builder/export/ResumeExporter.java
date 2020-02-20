package to.us.resume_builder.export;

import to.us.resume_builder.resume_components.Resume;
import to.us.resume_builder.resume_components.category.Category;

import java.util.List;
import java.util.StringJoiner;

public class ResumeExporter {
    private Resume resume;

    public ResumeExporter(Resume resume) {
        this.resume = resume;
    }

    public boolean export(String fileName) {
        String latexCode;
        try {
            latexCode = getLaTeXString();
        } catch (LaTeXGenerationException e) {
            return false;
        }

        return createResumePDF(fileName, latexCode);
    }

    private String getLaTeXString() throws LaTeXGenerationException {
        StringJoiner str = new StringJoiner(System.getProperty("line.separator"));

        List<Category> categories = null; // TODO: = resume.getCategories

        categories.forEach(c -> str.add(c.getLaTeXContent()));

        return str.toString();
    }

    private boolean createResumePDF(String fileName, String resumeLatex) {
        // TODO: stub

        return true;
    }
}
