package to.us.resume_builder.export;

import org.junit.jupiter.api.Test;
import to.us.resume_builder.ApplicationConfiguration;
import to.us.resume_builder.resume_components.Experience;
import to.us.resume_builder.resume_components.Resume;
import to.us.resume_builder.resume_components.category.*;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ResumeExporterTest {
    private static Resume getTestResume() {
        Resume r = new Resume();

        // Header
        String headerID = r.createCategory(CategoryType.HEADER);
        HeaderCategory header = (HeaderCategory) r.getCategoryByID(headerID);
        header.setName("Header");
        header.setDisplayName("Matthew McCaskill");
        header.setAddress("Goodyear, AZ 85395");
        header.setEmail("mattrm.dev");
        header.setLink("www.linkedin.com/in/matthew-mccaskill");
        header.setPhoneNumber("(623) 377-3772");

        // Profile
        String profileID = r.createCategory(CategoryType.TEXT);
        TextCategory profile = (TextCategory) r.getCategoryByID(profileID);
        profile.setName("Profile");
        profile.setDisplayName("Career Profile");
        profile.setText("Highly motivated Computer Science candidate with a strong foundation in software development. Strong ability to quickly resolve hardware and software problems to minimize disruptions in operations. Proven history of facilitating adoption of innovative solutions and training end users on new software. Excited to contribute to a mission-driven environment.");

        // Education
        String educationID = r.createCategory(CategoryType.EXPERIENCE);
        ExperienceCategory education = (ExperienceCategory) r.getCategoryByID(educationID);
        education.setName("Education");
        education.setDisplayName("Education");

        String baylorEduID = education.addExperience();
        Experience baylorEdu = education.getExperienceByID(baylorEduID);
        baylorEdu.setOrganization("Baylor University");
        baylorEdu.setLocation("Waco, Texas");
        baylorEdu.setDate("May 2022");
        baylorEdu.setTitle("Bachelor of Science in Computer Science");
        baylorEdu.getBulletByID(baylorEdu.addBullet()).setText("Secondary Major in Mathematics");
        baylorEdu.getBulletByID(baylorEdu.addBullet()).setText("GPA 4.0");
        baylorEdu.getBulletByID(baylorEdu.addBullet()).setText("Active member of Computing 4 Compassion");
        baylorEdu.getBulletByID(baylorEdu.addBullet()).setText("Member of Association for Computing Machinery (ACM) and Alpha Lambda Delta");
        baylorEdu.getBulletByID(baylorEdu.addBullet()).setText("Relevant Coursework: Introduction to Computer Science I & II, Discrete Mathematics, Data Structures, Software Engineering, and Algorithms");

        // Experience
        String experienceID = r.createCategory(CategoryType.EXPERIENCE);
        ExperienceCategory experience = (ExperienceCategory) r.getCategoryByID(experienceID);
        experience.setName("Experience");
        experience.setDisplayName("Experience");

        String siExpID = experience.addExperience();
        Experience siExp = experience.getExperienceByID(siExpID);
        siExp.setOrganization("Academic Support Programs, Baylor University");
        siExp.setLocation("Waco, Texas");
        siExp.setDate("2019-Present");
        siExp.setTitle("Supplemental Instruction Leader");
        siExp.getBulletByID(siExp.addBullet()).setText("Led biweekly group review sessions to improve student understanding of course content");
        siExp.getBulletByID(siExp.addBullet()).setText("Adapted teaching strategies to engage various learning styles");
        siExp.getBulletByID(siExp.addBullet()).setText("Functioned as an intermediary between students and professor");

        String kidsMinExpID = experience.addExperience();
        Experience kidsMinExp = experience.getExperienceByID(kidsMinExpID);
        kidsMinExp.setOrganization("The Refinery Christian Church");
        kidsMinExp.setLocation("Goodyear, Arizona");
        kidsMinExp.setDate("Summer 2019");
        kidsMinExp.setTitle("Kids Ministry Intern");
        kidsMinExp.getBulletByID(kidsMinExp.addBullet()).setText("Planned and executed weekend programming for children of all ages weekly");
        kidsMinExp.getBulletByID(kidsMinExp.addBullet()).setText("Learned effective management techniques to ensure smooth experience");
        kidsMinExp.getBulletByID(kidsMinExp.addBullet()).setText("Mentored new volunteers in teaching confidence and techniques");
        kidsMinExp.getBulletByID(kidsMinExp.addBullet()).setText("Responded to various emergency situations with calmness and efficiency");

        // Additional
        String miscID = r.createCategory(CategoryType.BULLETS);
        BulletCategory misc = (BulletCategory) r.getCategoryByID(miscID);
        misc.setName("Miscellaneous");
        misc.setDisplayName("Additional");
        misc.getBulletByID(misc.addBullet()).setText("Proficient in C++, Java SE, HTML, CSS, JavaScript, LaTeX");
        misc.getBulletByID(misc.addBullet()).setText("Comfortable with jQuery, Bootstrap, Python, C#, Intel x86 Assembly");
        misc.getBulletByID(misc.addBullet()).setText("Taught fifth and sixth grade students basic programming skills through Computing 4 Compassion’s igniteCS division");
        misc.getBulletByID(misc.addBullet()).setText("Served as a Host Group Leader with Antioch Community Church");
        misc.getBulletByID(misc.addBullet()).setText("Completed a Christian Leadership course, expanding knowledge of leadership and communication techniques");

        return r;
    }

    @Test
    public void exportDefault() {
        // Setup configuration
        ApplicationConfiguration.getInstance();

        // Create test resume
        Resume r = getTestResume();

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
        Resume r = getTestResume();

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