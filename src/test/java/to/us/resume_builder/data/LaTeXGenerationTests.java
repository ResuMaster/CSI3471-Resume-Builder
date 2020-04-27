package to.us.resume_builder.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import to.us.resume_builder.business.export_LaTeX.ResumeExporter;
import to.us.resume_builder.business.export_LaTeX.ResumeTemplate;
import to.us.resume_builder.data.resume_components.Experience;
import to.us.resume_builder.data.resume_components.IBulletContainer;
import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.category.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class LaTeXGenerationTests {
    private static Stream<Arguments> bulletTests() {
        return Stream.of(
            Arguments.of("test", "\\item test\n"),
            Arguments.of(null, ""),
            Arguments.of("", ""),
            Arguments.of("My name is Matthew", "\\item My name is Matthew\n")
        );
    }

    private static Stream<Arguments> experienceTests() {
        return Stream.of(
            Arguments.of("SI Leader", "Baylor", "Current", "Waco, Texas", "\\resumeexperience{Baylor}{SI Leader}{Waco, Texas}{Current}{\n}\n", "\\resumeexperience{Baylor}{SI Leader}{Waco, Texas}{Current}{\n\\item Bullet 1\n\\item Bullet 5\n}\n"),
            Arguments.of(null, null, null, null, "\\resumeexperience{}{}{}{}{\n}\n", "\\resumeexperience{}{}{}{}{\n\\item Bullet 1\n\\item Bullet 5\n}\n"),
            Arguments.of("", "", "", "", "\\resumeexperience{}{}{}{}{\n}\n", "\\resumeexperience{}{}{}{}{\n\\item Bullet 1\n\\item Bullet 5\n}\n")
        );
    }

    private static Stream<Arguments> categoryTests() {
        return Stream.of(
            Arguments.of("Internal Name", "Display Name", "Display Name"),
            Arguments.of("Internal Name", "Yo!*&", "Yo!*\\&"),
            Arguments.of("", "", ""),
            Arguments.of(null, null, "")
        );
    }

    private static void setupBulletHolder(IBulletContainer b) {
        b.getBulletByID(b.addBullet()).setText("Bullet 1");
        b.getBulletByID(b.addBullet()).setText("");
        b.getBulletByID(b.addBullet()).setText(null);
        String bulletID = b.addBullet();
        b.getBulletByID(bulletID).setText("Bullet 4");
        b.getBulletByID(bulletID).setVisible(false);
        b.getBulletByID(b.addBullet()).setText("Bullet 5");
    }

    private static void setupExperiences(ExperienceCategory c) {
        Experience e1 = c.getExperienceByID(c.addExperience());
        e1.setTitle("Title");
        e1.setOrganization("Org&");
        e1.setDate("Date");
        e1.setLocation("Loc");
        setupBulletHolder(e1);

        Experience e2 = c.getExperienceByID(c.addExperience());
        e2.setTitle("Title");
        e2.setOrganization("Org&");
        e2.setDate("Date");
        e2.setLocation("Loc");
        e2.setVisible(false);
        setupBulletHolder(e2);

        Experience e3 = c.getExperienceByID(c.addExperience());
        e3.setTitle("");
        e3.setOrganization("");
        e3.setDate(null);
        e3.setLocation(null);
        setupBulletHolder(e3);
    }

    private static Resume testResume() {
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

    @ParameterizedTest
    @DisplayName("Bullets")
    @MethodSource("bulletTests")
    public void bulletTests(String text, String expectedLaTeX) {
        // Create test category
        BulletCategory c = new BulletCategory("XXX");

        // Create bullet
        String id = c.addBullet();
        c.getBulletByID(id).setText(text);

        // Assertion
        assertEquals(expectedLaTeX, c.getBulletByID(id).formatLaTeXString(ResumeTemplate.DEFAULT));

        // Cleanup
        c.removeBullet(id);
    }

    @ParameterizedTest
    @DisplayName("Experiences")
    @MethodSource("experienceTests")
    public void experienceTests(String title, String org, String date, String loc, String expectedWithoutBullets, String expectedWithBullets) {
        // Create test category
        ExperienceCategory c = new ExperienceCategory("XXX");

        // Create experience
        String id1 = c.addExperience();
        Experience e1 = c.getExperienceByID(id1);
        String id2 = c.addExperience();
        Experience e2 = c.getExperienceByID(id2);
        e1.setTitle(title);
        e1.setOrganization(org);
        e1.setDate(date);
        e1.setLocation(loc);
        e2.setTitle(title);
        e2.setOrganization(org);
        e2.setDate(date);
        e2.setLocation(loc);
        setupBulletHolder(e2);

        // Assertion
        assertEquals(expectedWithoutBullets, c.getExperienceByID(id1).formatLaTeXString(ResumeTemplate.DEFAULT));
        assertEquals(expectedWithBullets, c.getExperienceByID(id2).formatLaTeXString(ResumeTemplate.DEFAULT));

        // Cleanup
        c.removeExperience(id1);
        c.removeExperience(id2);
    }

    @ParameterizedTest
    @DisplayName("Bullet Category")
    @MethodSource("categoryTests")
    public void bulletCategoryTest(String name, String displayName, String expectedCategoryStuff) {
        final String expectedWithoutBullets = "\\begin{resumebulletcategory}{" + expectedCategoryStuff + "}\n\\end{resumebulletcategory}\n";
        final String expectedWithBullets = "\\begin{resumebulletcategory}{" + expectedCategoryStuff + "}\n\\item Bullet 1\n\\item Bullet 5\n\\end{resumebulletcategory}\n";

        // Create categories
        BulletCategory b1 = new BulletCategory("XXX");
        BulletCategory b2 = new BulletCategory("XXX");
        BulletCategory b3 = new BulletCategory("XXX");
        BulletCategory b4 = new BulletCategory("XXX");
        b1.setName(name);
        b1.setDisplayName(displayName);
        b2.setName(name);
        b2.setDisplayName(displayName);
        b2.setVisible(false);
        b3.setName(name);
        b3.setDisplayName(displayName);
        b4.setName(name);
        b4.setDisplayName(displayName);
        b4.setVisible(false);

        // Setup bullets
        setupBulletHolder(b3);
        setupBulletHolder(b4);

        // Assertion
        assertEquals(expectedWithoutBullets, b1.formatLaTeXString(ResumeTemplate.DEFAULT));
        assertEquals(expectedWithoutBullets, b2.formatLaTeXString(ResumeTemplate.DEFAULT));
        assertEquals(expectedWithBullets, b3.formatLaTeXString(ResumeTemplate.DEFAULT));
        assertEquals(expectedWithBullets, b4.formatLaTeXString(ResumeTemplate.DEFAULT));
    }

    @ParameterizedTest
    @DisplayName("Experience Category")
    @MethodSource("categoryTests")
    public void experienceCategoryTest(String name, String displayName, String expectedCategoryStuff) {
        final String expected = "\\begin{resumeexperiencecategory}{" + expectedCategoryStuff + "}\n" +
            "\\resumeexperience{Org\\&}{Title}{Loc}{Date}{\n" +
            "\\item Bullet 1\n" +
            "\\item Bullet 5\n" +
            "}\n" +
            "\\resumeexperience{}{}{}{}{\n" +
            "\\item Bullet 1\n" +
            "\\item Bullet 5\n" +
            "}\n" +
            "\\end{resumeexperiencecategory}\n";

        // Create category
        ExperienceCategory c1 = new ExperienceCategory("XXX");
        c1.setName(name);
        c1.setDisplayName(displayName);
        ExperienceCategory c2 = new ExperienceCategory("XXX");
        c2.setName(name);
        c2.setDisplayName(displayName);
        c2.setVisible(false);

        // Setup experiences
        setupExperiences(c1);
        setupExperiences(c2);

        // Assertion
        assertEquals(expected, c1.formatLaTeXString(ResumeTemplate.DEFAULT));
        assertEquals(expected, c2.formatLaTeXString(ResumeTemplate.DEFAULT));
    }

    @ParameterizedTest
    @DisplayName("Text Category")
    @MethodSource("categoryTests")
    public void textCategoryTest(String name, String displayName, String expectedCategoryStuff) {
        final String expected1 = "\\resumetextcategory{" + expectedCategoryStuff + "}{Hi!}\n";
        final String expected2 = "\\resumetextcategory{" + expectedCategoryStuff + "}{Yo\\&\\}\n";
        final String expected3 = "\\resumetextcategory{" + expectedCategoryStuff + "}{}\n";

        // Create categories
        TextCategory c1 = new TextCategory("XXX");
        TextCategory c2 = new TextCategory("XXX");
        TextCategory c3 = new TextCategory("XXX");
        c1.setName(name);
        c1.setDisplayName(displayName);
        c2.setName(name);
        c1.setText("Hi!");
        c2.setDisplayName(displayName);
        c2.setVisible(false);
        c2.setText("Yo&\\");
        c3.setDisplayName(displayName);
        c3.setVisible(false);
        c3.setText(null);

        // Assertion
        assertEquals(expected1, c1.formatLaTeXString(ResumeTemplate.DEFAULT));
        assertEquals(expected2, c2.formatLaTeXString(ResumeTemplate.DEFAULT));
        assertEquals(expected3, c3.formatLaTeXString(ResumeTemplate.DEFAULT));
    }

    @ParameterizedTest
    @DisplayName("Header Category")
    @MethodSource("categoryTests")
    public void headerCategoryTest(String name, String displayName, String expectedCategoryStuff) {
        final String expected1 = "\\resumeheader{" + expectedCategoryStuff + "}{My}{Matthew}{is}{name}\n";
        final String expected2 = "\\resumeheader{" + expectedCategoryStuff + "}{}{\\#\\%Hello}{\\&\\&\\&\\&}{}\n";

        // Create categories
        HeaderCategory c1 = new HeaderCategory("XXX");
        HeaderCategory c2 = new HeaderCategory("XXX");
        c1.setName(name);
        c1.setDisplayName(displayName);
        c1.setAddress("My");
        c1.setEmail("name");
        c1.setLink("is");
        c1.setPhoneNumber("Matthew");
        c1.setVisible(false);
        c2.setName(name);
        c2.setDisplayName(displayName);
        c2.setAddress("");
        c2.setEmail(null);
        c2.setLink("&&&&");
        c2.setPhoneNumber("#%Hello");

        // Assertion
        assertEquals(expected1, c1.formatLaTeXString(ResumeTemplate.DEFAULT));
        assertEquals(expected2, c2.formatLaTeXString(ResumeTemplate.DEFAULT));
    }

    @Test
    @DisplayName("Full LaTeX Test")
    public void fullTest() {
        final String expected = "% Use article class with 11pt font\n" +
            "\\documentclass[11pt]{article}\n" +
            "\n" +
            "%%%%%%%%%%%%\n" +
            "% Packages %\n" +
            "%%%%%%%%%%%%\n" +
            "% Set margin to 1 in\n" +
            "\\usepackage{geometry}\n" +
            "\\geometry{letterpaper, margin=1in}\n" +
            "\n" +
            "% Use single spacing\n" +
            "\\usepackage{setspace}\n" +
            "\n" +
            "% Use UTF-8 encoding\n" +
            "\\usepackage[utf8]{inputenc}\n" +
            "\n" +
            "% Setup font\n" +
            "\\usepackage[defaultsans]{droidsans}\n" +
            "\\renewcommand*\\familydefault{\\sfdefault}\n" +
            "\\usepackage[T1]{fontenc}\n" +
            "\n" +
            "% Helper packages\n" +
            "\\usepackage{enumitem}\n" +
            "\\usepackage{xcolor}\n" +
            "\n" +
            "% Remove indent for paragraphs\n" +
            "\\setlength{\\parindent}{0pt}\n" +
            "\n" +
            "%%%%%%%%%%%%%%%%%%%%%%%%%%%\n" +
            "% Environments and Macros %\n" +
            "%%%%%%%%%%%%%%%%%%%%%%%%%%%\n" +
            "% Header Category\n" +
            "\\newcommand*{\\resumeheader}[5]{\n" +
            "    % Name and location\n" +
            "    \\begin{spacing}{1.2}\n" +
            "        \\textbf{\\huge #1} \\hfill #2\n" +
            "    \\end{spacing}\n" +
            "\n" +
            "    % Links\n" +
            "    \\begin{spacing}{.4}\n" +
            "        #4 \\hfill \\textbf{#3 • #5}\n" +
            "    \\end{spacing}\n" +
            "}\n" +
            "\n" +
            "% Text Category\n" +
            "\\newcommand{\\resumetextcategory}[2]{\n" +
            "    \\begin{spacing}{1.1}\n" +
            "        \\textbf{\\Large \\uppercase{#1}}\\\\\n" +
            "        #2\n" +
            "    \\end{spacing}\n" +
            "}\n" +
            "\n" +
            "% Bullet Category\n" +
            "\\newenvironment{resumebulletcategory}[1]{\n" +
            "    \\begin{spacing}{1.1}\n" +
            "        \\textbf{\\Large \\uppercase{#1}}\n" +
            "        \\begin{itemize}[topsep=1pt,itemsep=1pt,partopsep=.5pt, parsep=.5pt]\n" +
            "}{\n" +
            "        \\end{itemize}\n" +
            "    \\end{spacing}\n" +
            "}\n" +
            "\n" +
            "% Experience Category\n" +
            "\\newenvironment{resumeexperiencecategory}[1]{\n" +
            "    \\begin{spacing}{1.1}\n" +
            "        \\textbf{\\Large \\uppercase{#1}}\\\\\n" +
            "}{\n" +
            "    \\end{spacing}\n" +
            "}\n" +
            "\n" +
            "% Experience\n" +
            "\\newcommand{\\resumeexperience}[5]{\n" +
            "    \\textbf{\\uppercase {#1}} - #3\\hfill \\textbf{#4}\\\\\n" +
            "    \\textbf{#2}\n" +
            "\n" +
            "    \\begin{itemize}[topsep=1pt,itemsep=1pt,partopsep=.5pt, parsep=.5pt]\n" +
            "        #5\n" +
            "    \\end{itemize}\n" +
            "}\n" +
            "\n" +
            "% Separator Line\n" +
            "\\newcommand*{\\resumeseparator}{\n" +
            "    \\begin{spacing}{1.1}\n" +
            "        \\noindent\\rule{\\textwidth}{1pt}\n" +
            "    \\end{spacing}\n" +
            "}\n" +
            "\n" +
            "\\begin{document}\n" +
            "    % Remove page numbers\n" +
            "\t\\thispagestyle{empty}\n" +
            "\n" +
            "    \\resumeheader{Matthew McCaskill}{Goodyear, AZ 85395}{(623) 377-3772}{www.linkedin.com/in/matthew-mccaskill}{mattrm.dev}\n" +
            "\\resumeseparator\n" +
            "\\resumetextcategory{Career Profile}{Highly motivated Computer Science candidate with a strong foundation in software development. Strong ability to quickly resolve hardware and software problems to minimize disruptions in operations. Proven history of facilitating adoption of innovative solutions and training end users on new software. Excited to contribute to a mission-driven environment.}\n" +
            "\\resumeseparator\n" +
            "\\begin{resumeexperiencecategory}{Education}\n" +
            "\\resumeexperience{Baylor University}{Bachelor of Science in Computer Science}{Waco, Texas}{May 2022}{\n" +
            "\\item Secondary Major in Mathematics\n" +
            "\\item GPA 4.0\n" +
            "\\item Active member of Computing 4 Compassion\n" +
            "\\item Member of Association for Computing Machinery (ACM) and Alpha Lambda Delta\n" +
            "\\item Relevant Coursework: Introduction to Computer Science I \\& II, Discrete Mathematics, Data Structures, Software Engineering, and Algorithms\n" +
            "}\n" +
            "\\end{resumeexperiencecategory}\n" +
            "\\resumeseparator\n" +
            "\\begin{resumeexperiencecategory}{Experience}\n" +
            "\\resumeexperience{Academic Support Programs, Baylor University}{Supplemental Instruction Leader}{Waco, Texas}{2019-Present}{\n" +
            "\\item Led biweekly group review sessions to improve student understanding of course content\n" +
            "\\item Adapted teaching strategies to engage various learning styles\n" +
            "\\item Functioned as an intermediary between students and professor\n" +
            "}\n" +
            "\\resumeexperience{The Refinery Christian Church}{Kids Ministry Intern}{Goodyear, Arizona}{Summer 2019}{\n" +
            "\\item Planned and executed weekend programming for children of all ages weekly\n" +
            "\\item Learned effective management techniques to ensure smooth experience\n" +
            "\\item Mentored new volunteers in teaching confidence and techniques\n" +
            "\\item Responded to various emergency situations with calmness and efficiency\n" +
            "}\n" +
            "\\end{resumeexperiencecategory}\n" +
            "\\resumeseparator\n" +
            "\\begin{resumebulletcategory}{Additional}\n" +
            "\\item Proficient in C++, Java SE, HTML, CSS, JavaScript, LaTeX\n" +
            "\\item Comfortable with jQuery, Bootstrap, Python, C\\#, Intel x86 Assembly\n" +
            "\\item Taught fifth and sixth grade students basic programming skills through Computing 4 Compassion’s igniteCS division\n" +
            "\\item Served as a Host Group Leader with Antioch Community Church\n" +
            "\\item Completed a Christian Leadership course, expanding knowledge of leadership and communication techniques\n" +
            "\\end{resumebulletcategory}\n" +
            "\n" +
            "\\end{document}";

        ResumeExporter re = new ResumeExporter(testResume());

        assertEquals(expected, re.getLaTeXString(ResumeTemplate.DEFAULT));
    }
}
