package to.us.resume_builder.resume_components.category;

import to.us.resume_builder.resume_components.Experience;

import java.util.LinkedList;
import java.util.List;

public class ExperienceCategory extends Category {

    private List<Experience> experiences;

    public ExperienceCategory(String id) {
        super(id, CategoryType.EXPERIENCE);
        experiences = new LinkedList<>();
    }

    // TODO Implement
    public String addExperience(){
        return "";
    }

    // TODO Implement
    public void removeExperience(String id){

    }

    @Override
    public String getLaTeXContent() {
        return null;
    }
}
