package to.us.resume_builder.components;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class Resume {
    List<Category> categoryList;

    Resume(){
        categoryList = new LinkedList<>();
    }

    String createCategory(CategoryType type){
        Random rand = new Random();
        String id = String.valueOf(rand.nextInt(1000));

        switch(type){
            case HEADER:
                categoryList.add(new HeaderCategory(id));
                break;
            case TEXT:
                categoryList.add(new TextCategory(id));
                break;
            case EXPERIENCE:
                categoryList.add(new ExperienceCategory(id));
                break;
            case BULLETS:
                categoryList.add(new BulletCategory(id));
                break;
        }
        return id;
    }

}
