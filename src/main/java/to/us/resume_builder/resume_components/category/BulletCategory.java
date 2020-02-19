package to.us.resume_builder.resume_components.category;

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
    public String addBullet(){
        return "";
    }

    // TODO Implement
    public void removeBullet(String id){

    }

    @Override
    public String toLaTeXString() {
        return null;
    }
}
