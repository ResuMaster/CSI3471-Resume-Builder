package to.us.resume_builder;

import to.us.resume_builder.editorview.*;
import to.us.resume_builder.file.ResumeFile;
import to.us.resume_builder.file.ResumeFileManager;
import to.us.resume_builder.resume_components.Resume;
import to.us.resume_builder.resume_components.category.CategoryType;
import to.us.resume_builder.resume_components.category.TextCategory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                ResumeFile rf = ResumeFileManager.importFile("./src/main/resources/test.json");
                if (rf != null) {
                    new EditorFrame(rf);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
