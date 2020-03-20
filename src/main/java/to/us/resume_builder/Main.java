package to.us.resume_builder;

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
            FileRetriever t = new FileRetriever();
            String file = t.getFile();
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

/**
 * Class that will prompt the user to select a Resume Data File before
 * the application will open.
 *
 * @author Jacob Curtis
 */
class FileRetriever extends JPanel {
    /**
     * File dialog to retrieve the file from.
     */
    private FileDialog fileDialog;

    /**
     * Constructs the file retriever to only accept JSON.
     */
    public FileRetriever() {
        fileDialog = new FileDialog("json", this);
    }

    /**
     * Gets the Resume Data File the user wants to open in the edtior.
     *
     * @return The selected file.
     */
    public String getFile() {
        return fileDialog.getFile();
    }
}