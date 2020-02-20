package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.export.ResumeExportException;
import to.us.resume_builder.export.template.ResumeTemplate;
import to.us.resume_builder.export.template.StringTemplate;
import to.us.resume_builder.resume_components.Field;

import java.util.LinkedList;
import java.util.List;

public class BulletCategory extends Category {

    private List<Field> bullets;

    public BulletCategory(String id) {
        super(id, CategoryType.BULLETS);
        bullets = new LinkedList<>();
    }

    // TODO Implement
    public String addBullet() {
        return "";
    }

    // TODO Implement
    public void removeBullet(String id) {

    }

    @Override
    public String formatLaTeXString(ResumeTemplate template) {
        return template.getCategoryTemplate(this.type)
            .replaceVariable("title", this.displayName)
            .replaceVariable("content",
                bullets.stream()
                    .map(f -> f.formatLaTeXString(template))
                    .reduce((a, b) -> a + b)
                    .orElse("")
            )
            .toString();
    }
}
