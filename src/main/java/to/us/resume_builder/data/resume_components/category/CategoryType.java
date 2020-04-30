package to.us.resume_builder.data.resume_components.category;

/**
 * Class defining information about each CategoryType
 */
public enum CategoryType {
    HEADER("header", "Header",
        "A header which shows a name, an address, and \n" +
                   "two links. Usually, these are an email address \n" +
                   "and website URL."
    ),
    TEXT("text", "Text Blurb",
        "A section for paragraph-style text blocks."
    ),
    EXPERIENCE("experiences", "Experiences",
        "A list of experiences, such as education, \n" +
                   "work experience, volunteer experiences, etc. \n" +
                   "Each \"experience\" has a title, date range, \n" +
                   "location, and bullet points."
    ),
    BULLETS("bullets", "Bullet Points",
        "A simple bullet-point list."
    );

    /** Name of the fileName for the template for the CategoryType */
    private String templateFileName;
    /** The name of the CategoryType */
    private String name;
    /** Describes the CategoryType */
    private String description;

    /**
     * Constructs a CategoryType object
     * @param templateFileName the name of the Category
     * @param name the title for the CategoryType
     * @param description the description of the Category
     */
    CategoryType(String templateFileName, String name, String description) {
        this.templateFileName = templateFileName;
        this.name = name;
        this.description = description;
    }

    /**
     * Getter for the templateFileName
     * @return the templateFileName
     */
    public String getTemplateFileName() {
        return templateFileName;
    }

    /**
     * Getter for the Name
     * @return the Name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the description
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
