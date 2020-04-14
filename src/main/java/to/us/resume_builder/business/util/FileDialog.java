package to.us.resume_builder.business.util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;

/**
 * @author Micah
 */
public class FileDialog {
    JFileChooser chooser;
    Component parent;

    public FileDialog(String acceptedFile, Component parent) {
        this.parent = parent;
        chooser = new JFileChooser(".");
        chooser.setFileFilter(new FileNameExtensionFilter(null, acceptedFile));
    }

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