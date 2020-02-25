package to.us.resume_builder;

import to.us.resume_builder.export.ResumeExporter;
import to.us.resume_builder.export.ResumeTemplate;
import to.us.resume_builder.resume_components.Resume;
import to.us.resume_builder.resume_components.category.Category;
import to.us.resume_builder.resume_components.category.CategoryType;
import to.us.resume_builder.resume_components.category.HeaderCategory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Setup configuration
        ApplicationConfiguration.getInstance();

        // Create test resume
        Resume r = new Resume();
        String headerID = r.createCategory(CategoryType.HEADER);
        HeaderCategory header = (HeaderCategory) r.getCategory(headerID);
        header.setName("Header");
        header.setDisplayName("Matthew McCaskill");
        header.setAddress("Goodyear, AZ 85395");
        header.setEmail("mattrm.dev");
        header.setLink("www.linkedin.com/in/matthew-mccaskill");
        header.setPhone_number("(623) 377-3772");

        // Attempt to export the resume
        ResumeExporter re = new ResumeExporter(r);
        try {
            re.export("export.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
