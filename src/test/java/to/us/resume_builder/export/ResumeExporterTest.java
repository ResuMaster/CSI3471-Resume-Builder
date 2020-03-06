package to.us.resume_builder.export;

import org.junit.jupiter.api.Test;
import to.us.resume_builder.ApplicationConfiguration;
import to.us.resume_builder.Main;
import to.us.resume_builder.resume_components.Experience;
import to.us.resume_builder.resume_components.Resume;
import to.us.resume_builder.resume_components.category.*;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ResumeExporterTest {
    @Test
    public void exportDefault() {
        // Setup configuration
        ApplicationConfiguration.getInstance();

        // Create test resume
        Resume r = Main.getTestResume();

        // Attempt to export the resume
        ResumeExporter re = new ResumeExporter(r);
        try {
            assertTrue(re.export(Path.of("export1.pdf")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void exportNoHyphens() {
        // Setup configuration
        ApplicationConfiguration.getInstance();

        // Create test resume
        Resume r = Main.getTestResume();

        // Attempt to export the resume
        ResumeExporter re = new ResumeExporter(r);
        try {
            assertTrue(re.export(Path.of("export2.pdf"), ResumeTemplate.DEFAULT_NO_HYPHENS));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}