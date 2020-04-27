package to.us.resume_builder;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import to.us.resume_builder.business.ApplicationConfiguration;
import to.us.resume_builder.business.resume_file_management.ResumeFile;
import to.us.resume_builder.business.resume_file_management.ResumeFileManager;
import to.us.resume_builder.presentation.CreateOrOpenDialog;
import to.us.resume_builder.presentation.EditorFrame;
import to.us.resume_builder.business.util.FileDialog;

import javax.swing.*;
import java.io.IOException;

/**
 * The Main class to be run.
 *
 * @author Jacob Curtis
 * @author Micah Schiewe
 */
public class Main {
    /**
     * Retrieve the file to be opened and then load the editor.
     *
     * @param args Commands line arguments, unused.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                switch (ApplicationConfiguration.getInstance().getString("theme.color")) {
                    case "light":
                        UIManager.setLookAndFeel(new FlatIntelliJLaf());
                        break;
                    case "dark":
                        UIManager.setLookAndFeel(new FlatDarculaLaf());
                        break;
                    case "crap":
                    default:
                        break;
                }
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            new CreateOrOpenDialog();
        });
    }
}
