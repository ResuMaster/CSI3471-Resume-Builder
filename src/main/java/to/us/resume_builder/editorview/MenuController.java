package to.us.resume_builder.editorview;

import to.us.resume_builder.export.ResumeExporter;
import to.us.resume_builder.file.ResumeFile;
import to.us.resume_builder.file.ResumeFileManager;
import to.us.resume_builder.resume_components.Resume;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Jacob
 * @author Micah
 */
public class MenuController {
    private ResumeFile resume;

    public MenuController(ResumeFile r) {
        this.resume = r;
    }

    public void saveData(String path) {
        try {
            ResumeFileManager.exportFile(resume, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        saveData(resume.getFilePath());
    }

    public void export(String path) {
        ResumeExporter r = new ResumeExporter(resume.getResume());
        try {
            r.export(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
