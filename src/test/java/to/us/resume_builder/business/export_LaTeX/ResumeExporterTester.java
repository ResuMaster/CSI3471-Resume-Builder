package to.us.resume_builder.business.export_LaTeX;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.category.Category;
import to.us.resume_builder.data.resume_components.category.CategoryType;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ResumeExporterTester {
    static ResumeExporterMock re;
    static Resume r;

    @BeforeAll
    static void init() {
        r = new Resume();
        re = new ResumeExporterMock(r);
    }

    @Test
    public void testExport() {
        try {
            String id = r.createCategory(CategoryType.BULLETS);
            Category temp = r.getCategoryByID(id);
            temp.setDisplayName("Bullet");

            String laTexString = "";
            for (Category c: r.getCategoryList()) laTexString += c.formatLaTeXString(ResumeTemplate.DEFAULT);

            assertEquals(true, re.export(Path.of("test_files")));
        }
        catch(IOException e) {
            System.out.println(e.getCause() + " " + e.getMessage());
        }
    }
}
