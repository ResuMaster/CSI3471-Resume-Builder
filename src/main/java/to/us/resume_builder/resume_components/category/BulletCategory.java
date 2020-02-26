package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.resume_components.Bullet;

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
    public void removeBullet(String id){

    }

    @Override
    public String toLaTeXString() {
        return null;
    }
}
