package to.us.resume_builder.resume_components.category;

<<<<<<< HEAD
import to.us.resume_builder.export.ResumeTemplate;
import to.us.resume_builder.resume_components.Field;
=======
import to.us.resume_builder.resume_components.Bullet;
>>>>>>> master

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BulletCategory extends Category {

    private List<Bullet> bullets;

    public BulletCategory(String id) {
        super(id, CategoryType.BULLETS);
        bullets = new LinkedList<>();
    }

    // TODO Realize that this code is duplicated in Experience
    public String addBullet(){
        // generate id with current id in the front
        Random rand = new Random();
        String id = this.id + rand.nextInt(1000);
        // TODO check id to make sure that it is not a duplicate

        // add new element to bullets
        bullets.add(new Bullet(id));
        return id;
    }

    // TODO Implement
    public void removeBullet(String id) {

    }

    /**
     * Get the result of serializing this object using the specified template.
     *
     * @param template The template to format this object with.
     *
     * @return A String representing the object in the LaTeX template.
     * @author Matthew McCaskill
     */
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
