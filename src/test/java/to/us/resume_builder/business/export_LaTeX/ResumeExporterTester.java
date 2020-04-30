package to.us.resume_builder.business.export_LaTeX;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import to.us.resume_builder.business.server_connect.request.FailedRequestException;
import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.category.BulletCategory;
import to.us.resume_builder.data.resume_components.category.CategoryType;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the ResumeExporter class
 * @author Brooklynn Stone
 */
public class ResumeExporterTester {
    static ResumeExporterMock re;
    static Resume r;

    /**
     * Initialize the {@link ResumeExporter} with a {@link Resume} containing a {@link BulletCategory}
     */
    @BeforeAll
    static void init() {
        r = new Resume();
        String id = r.createCategory(CategoryType.BULLETS);
        BulletCategory bc = (BulletCategory)(r.getCategoryByID(id));
        bc.setDisplayName("Bullet Category");
        re = new ResumeExporterMock(r);
    }

    /**
     * Test exporting with a null path
     */
    @Test
    public void testExport() {
        assertEquals(false, re.export(null));
    }
}
