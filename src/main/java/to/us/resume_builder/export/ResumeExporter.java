package to.us.resume_builder.export;

import to.us.resume_builder.export.template.ResumeTemplate;
import to.us.resume_builder.export.template.StringTemplate;
import to.us.resume_builder.resume_components.Resume;
import to.us.resume_builder.resume_components.category.Category;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringJoiner;

public class ResumeExporter {
    private Resume resume;

    public ResumeExporter(Resume resume) {
        this.resume = resume;
    }

    public boolean export(String fileName) throws IOException {
        String latexCode;
        latexCode = getLaTeXString(ResumeTemplate.DEFAULT);

        // TODO: remove; debug
        Files.writeString(Path.of("export.txt"), latexCode);

        return createResumePDF(fileName, latexCode);
    }

    private String getLaTeXString(ResumeTemplate template) {
        StringJoiner sb = new StringJoiner(template.getSeparatorTemplate().toString());

        List<Category> categories = resume.getCategoryList();

        categories.forEach(c -> sb.add(c.formatLaTeXString(template)));

        return template.getLatexTemplate()
            .replaceVariable("content", sb.toString())
            .toString();
    }

    private boolean createResumePDF(String fileName, String resumeLatex) {
        // TODO: stub

        return true;
    }
}
