package to.us.resume_builder.editorview;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditorMenuBar extends JMenuBar implements ActionListener {
    private JMenu file;
    private JMenuItem saveDataFile;
    private JMenuItem exportDataFile;
    private JMenuItem exportResume;
    private JMenuItem exit;
    private EditorController controller = null;

    public void setController(EditorController controller) {
        this.controller = controller;
    }

    public EditorMenuBar() {
        file = new JMenu("File");
        file.add(saveDataFile = new JMenuItem("Save Data File"));
        file.add(exportDataFile = new JMenuItem("Export Data File"));
        file.add(exportResume = new JMenuItem("Export Resume"));
        file.add(exit = new JMenuItem("Exit"));

        saveDataFile.addActionListener(this);
        saveDataFile.setActionCommand("saveDataFile");
        exportDataFile.addActionListener(this);
        exportDataFile.setActionCommand("exportDataFile");
        exportResume.addActionListener(this);
        exportResume.setActionCommand("exportResume");
        exit.addActionListener(this);
        exit.setActionCommand("exit");

        add(file);
        revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO connect with functionality
    }
}
