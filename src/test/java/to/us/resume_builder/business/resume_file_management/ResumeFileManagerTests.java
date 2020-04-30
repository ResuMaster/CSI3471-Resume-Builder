package to.us.resume_builder.business.resume_file_management;

import org.junit.jupiter.api.Test;
import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.category.BulletCategory;
import to.us.resume_builder.data.resume_components.category.ExperienceCategory;
import to.us.resume_builder.data.resume_components.category.HeaderCategory;
import to.us.resume_builder.data.resume_components.category.TextCategory;

import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

public class ResumeFileManagerTests {
    @Test
    public void importFileTest() {
        String json = "{\n" +
            "  \"metadata\": {\n" +
            "    \"created\": \"Feb 23, 2020, 10:25:31 PM\",\n" +
            "    \"last_modified\": \"Feb 23, 2020, 10:25:31 PM\",\n" +
            "    \"email\": \"bob@gmail.com\"\n" +
            "  },\n" +
            "  \"resume\": {\n" +
            "    \"category_list\": [\n" +
            "      {\n" +
            "        \"link\": \"http://resume-builder.us.to/\",\n" +
            "        \"email\": \"john_cena@baylor.edu\",\n" +
            "        \"phone_number\": \"222-222-2222\",\n" +
            "        \"address\": \"9999 WWE Street\\nWest Newbury, MA 02761\",\n" +
            "        \"type\": \"HEADER\",\n" +
            "        \"name\": \"john\",\n" +
            "        \"display_name\": \"cena\",\n" +
            "        \"id\": \"590\",\n" +
            "        \"visible\": true\n" +
            "      },\n" +
            "      {\n" +
            "        \"text\": \"sample text\",\n" +
            "        \"type\": \"TEXT\",\n" +
            "        \"name\": \"noah\",\n" +
            "        \"display_name\": \"lee\",\n" +
            "        \"id\": \"617\",\n" +
            "        \"visible\": false\n" +
            "      },\n" +
            "      {\n" +
            "        \"experiences\": [],\n" +
            "        \"type\": \"EXPERIENCE\",\n" +
            "        \"name\": \"ash\",\n" +
            "        \"display_name\": \"ketchup\",\n" +
            "        \"id\": \"55\",\n" +
            "        \"visible\": true\n" +
            "      },\n" +
            "      {\n" +
            "        \"bullets\": [],\n" +
            "        \"column_count\": 0,\n" +
            "        \"type\": \"BULLETS\",\n" +
            "        \"name\": \"michael\",\n" +
            "        \"display_name\": \"faraday\",\n" +
            "        \"id\": \"805\",\n" +
            "        \"visible\": false\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";
        assertDoesNotThrow(() -> {
            ResumeFile r = ResumeFileManager.importFile(new StringReader(json));
            assertNotNull(r);
            assertNotNull(r.getMetadata());
            assertNotNull(r.getResume());
            Resume resume = r.getResume();
            assertNotNull(resume.getCategoryList());
            resume.getCategoryList().forEach(e -> {
                assertNotNull(e);
                assertNotNull(e.getDisplayName());
                assertNotNull(e.getName());
                switch (e.getType()) {
                    case HEADER: {
                        HeaderCategory c = (HeaderCategory)e;
                        assertEquals("http://resume-builder.us.to/", c.getLink());
                        assertEquals("john_cena@baylor.edu", c.getEmail());
                        assertEquals("222-222-2222", c.getPhoneNumber());
                        assertEquals("9999 WWE Street\nWest Newbury, MA 02761", c.getAddress());
                        assertEquals("john", c.getName());
                        assertEquals("cena", c.getDisplayName());
                        assertEquals("590", c.getID());
                        assertTrue(c.getVisible());
                        break;
                    }
                    case TEXT: {
                        TextCategory c = (TextCategory)e;
                        assertEquals("noah", c.getName());
                        assertEquals("lee", c.getDisplayName());
                        assertEquals("617", c.getID());
                        assertEquals("sample text", c.getText());
                        assertFalse(c.getVisible());
                        break;
                    }
                    case EXPERIENCE: {
                        ExperienceCategory c = (ExperienceCategory)e;
                        assertEquals("ash", c.getName());
                        assertEquals("ketchup", c.getDisplayName());
                        assertEquals("55", c.getID());
                        assertNotNull(c.getExperienceList());
                        assertEquals(0, c.getExperienceList().size());
                        assertTrue(c.getVisible());
                        break;
                    }
                    case BULLETS: {
                        BulletCategory c = (BulletCategory)e;
                        assertEquals("michael", c.getName());
                        assertEquals("faraday", c.getDisplayName());
                        assertEquals("805", c.getID());
                        assertNotNull(c.getBulletList());
                        assertEquals(0, c.getBulletList().size());
                        assertEquals(0, c.getColumn());
                        assertFalse(c.getVisible());
                        break;
                    }
                }
            });
        });
    }

    @Test
    public void exportFileTest() {
        StringWriter json = new StringWriter();
        ResumeFile r = new ResumeFile(new Metadata(), new Resume());
        assertDoesNotThrow(() -> {
            ResumeFileManager.exportFile(r, json);
            ResumeFile copy = ResumeFileManager.importFile(new StringReader(json.toString()));
            assertNotNull(copy);
            assertNotNull(copy.getResume());
            assertNotNull(copy.getResume().getCategoryList());
            assertEquals(0, copy.getResume().getCategoryList().size());
        });
    }

    @Test
    public void emptyJsonImportTest() {
        assertDoesNotThrow(() ->
            assertNull(ResumeFileManager.importFile(new StringReader("")))
        );
    }

    @Test
    public void invalidJsonImportTest() {
        String json = "{\n" +
            "  \"met\": {\n" +
            "  },\n" +
            "  \"res\": {\n" +
            "    \"category_list\": [\n" +
            "    ]\n" +
            "  }\n" +
            "}";
        assertDoesNotThrow(() -> {
            ResumeFile r = ResumeFileManager.importFile(new StringReader(json));
            assertNotNull(r);
            assertNull(r.getMetadata());
            assertNull(r.getResume());
        });
    }
}
