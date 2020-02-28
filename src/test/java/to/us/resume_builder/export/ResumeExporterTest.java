package to.us.resume_builder.export;

import org.junit.jupiter.api.Test;
import to.us.resume_builder.ApplicationConfiguration;
import to.us.resume_builder.resume_components.Experience;
import to.us.resume_builder.resume_components.Resume;
import to.us.resume_builder.resume_components.category.CategoryType;
import to.us.resume_builder.resume_components.category.ExperienceCategory;
import to.us.resume_builder.resume_components.category.HeaderCategory;
import to.us.resume_builder.resume_components.category.TextCategory;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ResumeExporterTest {
    @Test
    void export() {
        // Setup configuration
        ApplicationConfiguration.getInstance();

        // Create test resume
        Resume r = new Resume();

        // Header
        String headerID = r.createCategory(CategoryType.HEADER);
        HeaderCategory header = (HeaderCategory) r.getCategory(headerID);
        header.setName("Header");
        header.setDisplayName("Matthew McCaskill");
        header.setAddress("Goodyear, AZ 85395");
        header.setEmail("mattrm.dev");
        header.setLink("www.linkedin.com/in/matthew-mccaskill");
        header.setPhone_number("(623) 377-3772");

        // Profile
        String profileID = r.createCategory(CategoryType.TEXT);
        TextCategory profile = (TextCategory) r.getCategory(profileID);
        profile.setName("Profile");
        profile.setDisplayName("Career Profile");
        profile.setText("Highly motivated Computer Science candidate with a strong foundation in software development. Strong ability to quickly resolve hardware and software problems to minimize disruptions in operations. Proven history of facilitating adoption of innovative solutions and training end users on new software. Excited to contribute to a mission-driven environment.");

        // Education
        String educationID = r.createCategory(CategoryType.EXPERIENCE);
        ExperienceCategory education = (ExperienceCategory) r.getCategory(educationID);
        String baylorEduID = education.addExperience();
        Experience baylorEdu = education.getExperience(baylorEduID);


        // Attempt to export the resume
        ResumeExporter re = new ResumeExporter(r);
        try {
            re.export(Path.of("export.pdf"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}