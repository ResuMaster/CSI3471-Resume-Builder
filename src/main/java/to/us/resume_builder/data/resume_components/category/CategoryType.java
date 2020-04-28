package to.us.resume_builder.data.resume_components.category;


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

    private String templateFileName;
    private String name;
    private String description;

    CategoryType(String templateFileName, String name, String description) {
        this.templateFileName = templateFileName;
        this.name = name;
        this.description = description;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
