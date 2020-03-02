package to.us.resume_builder.export;

import org.junit.jupiter.api.Test;
import to.us.resume_builder.ApplicationConfiguration;
import to.us.resume_builder.resume_components.Experience;
import to.us.resume_builder.resume_components.Resume;
import to.us.resume_builder.resume_components.category.*;

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
        education.setName("Education");
        education.setDisplayName("Education");

        String baylorEduID = education.addExperience();
        Experience baylorEdu = education.getExperience(baylorEduID);
        baylorEdu.setOrganization("Baylor University");
        baylorEdu.setLocation("Waco, Texas");
        baylorEdu.setDate("May 2022");
        baylorEdu.setTitle("Bachelor of Science in Computer Science");
        baylorEdu.addBullet();  // TODO: link
        baylorEdu.addBullet();
        baylorEdu.addBullet();
        baylorEdu.addBullet();
        baylorEdu.addBullet();

        // Experience
        String experienceID = r.createCategory(CategoryType.EXPERIENCE);
        ExperienceCategory experience = (ExperienceCategory) r.getCategory(experienceID);
        experience.setName("Experience");
        experience.setDisplayName("Experience");

        String siExpID = experience.addExperience();
        Experience siExp = experience.getExperience(siExpID);
        siExp.setOrganization("Academic Support Programs, Baylor University");
        siExp.setLocation("Waco, Texas");
        siExp.setDate("2019-Present");
        siExp.setTitle("Supplemental Instruction Leader");
        siExp.addBullet();  // TODO: link
        siExp.addBullet();
        siExp.addBullet();

        String kidsMinExpID = experience.addExperience();
        Experience kidsMinExp = experience.getExperience(kidsMinExpID);
        kidsMinExp.setOrganization("The Refinery Christian Church");
        kidsMinExp.setLocation("Goodyear, Arizona");
        kidsMinExp.setDate("Summer 2019");
        kidsMinExp.setTitle("Kids Ministry Intern");
        kidsMinExp.addBullet();  // TODO: link
        kidsMinExp.addBullet();
        kidsMinExp.addBullet();
        kidsMinExp.addBullet();

        // Additional
        String miscID = r.createCategory(CategoryType.BULLETS);
        BulletCategory misc = (BulletCategory) r.getCategory(miscID);
        misc.setName("Miscellaneous");
        misc.setDisplayName("Additional");
        misc.addBullet();  // TODO: link
        misc.addBullet();
        misc.addBullet();
        misc.addBullet();
        misc.addBullet();

        // Attempt to export the resume
        ResumeExporter re = new ResumeExporter(r);
        try {
            re.export(Path.of("export.pdf"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}