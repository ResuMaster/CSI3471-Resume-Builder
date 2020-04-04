package to.us.resume_builder;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import to.us.resume_builder.main_window.*;
import to.us.resume_builder.file.ResumeFile;
import to.us.resume_builder.file.ResumeFileManager;
import to.us.resume_builder.util.FileDialog;

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

            FileDialog fileDialog = new FileDialog("json", null);
            String file = fileDialog.getFile();
            // Cancel opening the editor if the user did not select a file.
            if (file == null) {
                return;
            }
            try {
                ResumeFile rf = ResumeFileManager.importFile(file);
                if (rf != null) {
                    new EditorFrame(rf);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
