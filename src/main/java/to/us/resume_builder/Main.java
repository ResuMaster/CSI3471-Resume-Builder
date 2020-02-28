package to.us.resume_builder;

import to.us.resume_builder.editorview.EditorCategorySelector;
import to.us.resume_builder.editorview.EditorMenuBar;
import to.us.resume_builder.editorview.EditorStage;
import to.us.resume_builder.resume_components.category.TextCategory;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Tester");
            f.setLayout(new BorderLayout());
            f.setJMenuBar(new EditorMenuBar());
            f.add(new EditorCategorySelector(), BorderLayout.WEST);
            f.add(new EditorStage(new TextCategory("heya")), BorderLayout.CENTER);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.pack();
            f.setVisible(true);
        });
    }
}
