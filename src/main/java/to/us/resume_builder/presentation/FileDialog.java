package to.us.resume_builder.presentation;

import java.awt.Component;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Provides standard utility for retrieving a filename from the user.
 * 
 * @author Micah
 */
public class FileDialog {
    private JFileChooser chooser;
    private Component parent;

    /**
     * Creates a new, reusable component that can access
     * 
     * @param acceptedFile The file extension to filter for (e.g. 'pdf' for .pdf
     *                     files)
     * @param parent       The component to center the dalog on. Can be null.
     */
    public FileDialog(String acceptedFile, Component parent) {
        this.parent = parent;
        chooser = new JFileChooser(".");
        chooser.setFileFilter(new FileNameExtensionFilter(acceptedFile, acceptedFile));
    }

    /**
     * Retrieves a filename from the user. Will return null if the user cancels the
     * operation, or if an IO error occurs while getting the path to the requested
     * file.
     * 
     * @return The path to the selected file, or null if the user cancels or an
     *         error occurs.
     */
    public String getFile() {
        String toReturn = null;
        int result;

        result = chooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                toReturn = chooser.getSelectedFile().getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                toReturn = null;
            }
        }

        return toReturn;
    }
}
