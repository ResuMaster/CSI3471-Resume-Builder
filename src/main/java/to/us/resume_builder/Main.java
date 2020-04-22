package to.us.resume_builder;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.logging.LogManager;

import javax.swing.SwingUtilities;

import to.us.resume_builder.business.resume_file_management.ResumeFile;
import to.us.resume_builder.business.resume_file_management.ResumeFileManager;
import to.us.resume_builder.business.util.FileDialog;
import to.us.resume_builder.presentation.EditorFrame;

/**
 * The Main class to be run.
 *
 * @author Jacob Curtis
 * @author Micah Schiewe
 */
public class Main {
    private static final String LOG_CONFIG_FILE = "log.properties";

    /**
     * Initialize the logger.
     */
    static {
        try (InputStream reader = Main.class.getClassLoader().getResourceAsStream(LOG_CONFIG_FILE)) {
            // Read in the configurations
            LogManager lm = LogManager.getLogManager();
            lm.readConfiguration(reader);
        } catch (IOException | InvalidPathException ex) {
            // Error: could not read configurations, report the failure
            ex.printStackTrace();
            try (PrintWriter writer = new PrintWriter(
                    Files.newBufferedWriter(Paths.get("report_log_failure"), StandardCharsets.UTF_8))) {
                writer.println("Could not find " + LOG_CONFIG_FILE + "; logging via stdout as a fallback. Reason:");
                ex.printStackTrace(writer);
                writer.flush();
            } catch (IOException | InvalidPathException e2) {
                // Error reporting the error
                e2.printStackTrace();
                System.err.println("Caused by attempting to report:");
                ex.printStackTrace();
                System.err.println("Failed to persist error record");
            }
        }
    }

    /**
     * Retrieve the file to be opened and then load the editor.
     *
     * @param args Commands line arguments, unused.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
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
