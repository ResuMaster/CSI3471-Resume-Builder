package to.us.resume_builder.resume_components.category;


public enum CategoryType {
    HEADER("header"),
    TEXT("text"),
    EXPERIENCE("experiences"),
    BULLETS("bullets");

    private String templateFileName;

    CategoryType(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }
}
