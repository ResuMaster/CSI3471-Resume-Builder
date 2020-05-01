package to.us.resume_builder.data.resume_components.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import to.us.resume_builder.business.export_LaTeX.ResumeTemplate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link HeaderCategory}
 * @author Brooklynn Stone
 */
public class HeaderCategoryTester extends CategoryTester {
    /**
     * {@link HeaderCategory} object to perform tests on
     */
    private HeaderCategory hc;

    /**
     * Initializes a Header Category with test input
     */
    @BeforeEach
    public void init() {
        hc = new HeaderCategory("12345");
        hc.setAddress("1234 Nowhere lane, Waco, TX 76798");
        hc.setEmail("stone.brooklynn@gmail.com");
        hc.setLink("mylinkedin.com");
        hc.setPhoneNumber("123 456-7890");
        hc.setDisplayName("Display Name");
        hc.setName("Header Category");
    }

    @Test
    @Override
    void testGetType() {
        assertEquals(CategoryType.HEADER, hc.getType());
    }

    @Test
    @Override
    void testDisplayName() {
        assertEquals("Display Name", hc.getDisplayName());
    }

    @Test
    @Override
    void testName() {
        assertEquals("Header Category", hc.getName());
    }

    /**
     * Verify the correct LaTeX String is generated
     */
    @Test
    void testFormatLaTeXString() {
        String laTeX = "\\resumeheader{Display Name}" +
                "{1234 Nowhere lane, Waco, TX 76798}" +
                "{123 456-7890}" +
                "{mylinkedin.com}" +
                "{stone.brooklynn@gmail.com}\n";
        assertEquals(laTeX, hc.formatLaTeXString(ResumeTemplate.DEFAULT));
    }
}
