package to.us.resume_builder;

import to.us.resume_builder.export.ResumeExportException;
import to.us.resume_builder.export.ResumeExporter;
import to.us.resume_builder.export.template.ResumeTemplate;
import to.us.resume_builder.export.template.StringTemplate;
import to.us.resume_builder.resume_components.Resume;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Setup configuration
        ApplicationConfiguration.getInstance();

        System.out.println("Hello, world!");

        ResumeTemplate def = ResumeTemplate.DEFAULT;

        StringTemplate s = new StringTemplate("<name> <date> \\<otherinfo>");

        s.replaceVariable("name", "Matthew")
            .replaceVariable("date", "McCaskill");

        Resume r = new Resume();

        ResumeExporter re = new ResumeExporter(r);

        try {
            re.export("export.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(s);
    }
}
