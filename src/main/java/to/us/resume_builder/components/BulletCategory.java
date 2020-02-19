package to.us.resume_builder.components;

import java.util.LinkedList;
import java.util.List;

public class BulletCategory extends Category {

    List<Field> bullets;

    BulletCategory(String id) {
        super(id, CategoryType.BULLETS);
        bullets = new LinkedList<>();
    }

    // TODO Implement
    void addBullet(){

    }

    // TODO Implement
    void removeBullet(String id){

    }

    @Override
    public String toLaTeXString() {
        return null;
    }
}
