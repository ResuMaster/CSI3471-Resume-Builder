package to.us.resume_builder.business.export_LaTeX;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import to.us.resume_builder.business.server_connect.PDFFacade;
import to.us.resume_builder.business.server_connect.request.FailedRequestException;
import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.category.BulletCategory;
import to.us.resume_builder.data.resume_components.category.CategoryType;

import static org.junit.jupiter.api.Assertions.*;

import mockit.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Tests the {@link ResumeExporter} class
 * @author Brooklynn Stone
 */
public class ResumeExporterTester {
    /**
     * Testing of the {@link ResumeExporter} class
     */
    static ResumeExporter re;
    /**
     * A {@link Resume} to use for testing
     */
    static Resume r;

    @Mocked
    PDFFacade pdf;


    /**
     * Initialize the {@link ResumeExporter} with a {@link Resume} containing a {@link BulletCategory}
     */
    @BeforeAll
    static void init() {
        r = new Resume();
        String id = r.createCategory(CategoryType.BULLETS);
        BulletCategory bc = (BulletCategory)(r.getCategoryByID(id));
        bc.setDisplayName("Bullet Category");
        re = new ResumeExporter(r);
    }

    /**
     * Test exporting with a null path
     */
    @Test
    public void testExport() {
        try {
            new Expectations() {
                {
                    try {
                        pdf.getPDF(anyString);
                        result = new byte[]{1, 2, 3};
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (FailedRequestException e) {
                        e.printStackTrace();
                    }
                }
            };
        }catch(ExceptionInInitializerError e) {}

        assertEquals(false, re.export(null));
    }

    /**
     * Tests exporting using a mocked getPDF method.
     */
    @Test
    public void testExport2() {
        try {
            new Expectations() {{
                pdf.getPDF(anyString);
                result = new byte[]{1, 2, 3};
            }};
            new Expectations() {{
                Files.write(Path.of(anyString), (byte[]) any);
                result = true;
            }};
        }catch (ExceptionInInitializerError e) {} catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FailedRequestException e) {
            e.printStackTrace();
        }

        assertEquals(false, re.export(Path.of("testFolder")));
    }
}
