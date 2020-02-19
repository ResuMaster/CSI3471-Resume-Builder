package to.us.resume_builder.components;

import java.util.LinkedList;
import java.util.List;

public class ExperienceCategory extends Category {

    List<Item> experience;

    ExperienceCategory(String id) {
        super(id, CategoryType.EXPERIENCE);
        experience = new LinkedList<>();
    }

    // TODO Implement
    public void addExperience(){

    }

    // TODO Implement
    public void removeExperience(String id){

    }

    @Override
    public String toLaTeXString() {
        return null;
    }
}
