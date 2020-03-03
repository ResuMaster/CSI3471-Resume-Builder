package to.us.resume_builder;

import to.us.resume_builder.editorview.*;
import to.us.resume_builder.resume_components.Experience;
import to.us.resume_builder.resume_components.category.BulletCategory;
import to.us.resume_builder.resume_components.category.ExperienceCategory;
import to.us.resume_builder.resume_components.category.HeaderCategory;
import to.us.resume_builder.resume_components.category.TextCategory;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        BulletCategory bc = new BulletCategory("12345");
        bc.setDisplayName("Bullet Category");
        bc.setName("Bullet Category");
        bc.addBullet();

        ExperienceCategory expC = new ExperienceCategory("12346");
        expC.setDisplayName("Experience Category");
        expC.setName("Experience Category");

        HeaderCategory hc = new HeaderCategory("12347");
        hc.setDisplayName("Header Category");
        hc.setName("Header Category");
        hc.setAddress("my address");
        hc.setEmail("my email");
        hc.setLink("my link");
        hc.setPhoneNumber("my phone number");

        TextCategory tc = new TextCategory("12348");
        tc.setDisplayName("Text Category");
        tc.setName("Text Category");
        tc.setText("my text");

        Experience ex = new Experience("123");
        ex.setDate("MArch");
        ex.setLocation("Waco");
        ex.setOrganization("Baylor");
        
//        frame.add(new BulletCategoryEditPane(bc));
//        frame.add(new ExperienceCategoryEditPane(expC));
//        frame.add(new HeaderCategoryEditPane(hc));
//        frame.add(new TextCategoryEditPane(tc));
//        frame.add(new ExperienceComponent(ex));
        frame.setContentPane(new ExperienceComponent(ex));
        frame.pack();
        frame.setVisible(true);
    }
}
