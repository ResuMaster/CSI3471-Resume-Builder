package to.us.resume_builder.resume_components;

import to.us.resume_builder.resume_components.category.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class Resume {
    private List<Category> categoryList;

    public Resume(){
        categoryList = new LinkedList<>();
    }

    public String createCategory(CategoryType type){
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

    public Category getCategory(String id) {
        return categoryList.stream()
            .filter(c -> c.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }
}
