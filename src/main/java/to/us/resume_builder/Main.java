package to.us.resume_builder;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import to.us.resume_builder.business.ApplicationConfiguration;
import to.us.resume_builder.presentation.CreateOrOpenDialog;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * The Main class to be run.
 *
 * @author Jacob Curtis
 * @author Micah Schiewe
 */
public class Main {
    private static Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * Retrieve the file to be opened and then load the editor.
     *
     * @param args Commands line arguments, unused.
     */
    public static void main(String[] args) {
        // Setup logger
        LogManager lm = LogManager.getLogManager();
        try {
            lm.readConfiguration(Thread.currentThread().getContextClassLoader().getResourceAsStream("log.properties"));
        } catch (IOException e) {
            LOGGER.severe("Couldn't read configuration file.");
        }

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
