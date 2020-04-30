package to.us.resume_builder.presentation;

import to.us.resume_builder.business.resume_file_management.Metadata;
import to.us.resume_builder.business.resume_file_management.ResumeFile;
import to.us.resume_builder.business.resume_file_management.ResumeFileManager;
import to.us.resume_builder.business.util.FileDialog;
import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.category.CategoryType;
import to.us.resume_builder.data.resume_components.category.HeaderCategory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Logger;

public class CreateOrOpenDialog extends JFrame {
    private static Logger LOGGER = Logger.getLogger(CreateOrOpenDialog.class.getName());

    private static final int LOGO_PADDING = 50;

    private static final Resume DEFAULT_RESUME = new Resume();

    static {
        String headerID = DEFAULT_RESUME.createCategory(CategoryType.HEADER);
        HeaderCategory header = (HeaderCategory) DEFAULT_RESUME.getCategoryByID(headerID);
        header.setDisplayName("Your Name Here");
        header.setName("Header");
    }

    public CreateOrOpenDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        SimpleImagePanel logo = new SimpleImagePanel("images/logo.png", 0.4f);
        logo.setBorder(new EmptyBorder(LOGO_PADDING, 0, LOGO_PADDING, 0));
        panel.add(logo, BorderLayout.NORTH);

        JPanel buttons = new JPanel();
        buttons.setBorder(new EmptyBorder(20, 40, 20, 40));
        GridLayout buttonLayout = new GridLayout(3, 1);
        buttonLayout.setHgap(10);
        buttonLayout.setVgap(40);
        buttons.setLayout(buttonLayout);
        JButton createButton = new JButton("Create a New Resume");
        createButton.addActionListener(evt -> {
            LOGGER.info("Creating new resume.");
            new EditorFrame(new ResumeFile(DEFAULT_RESUME));
            this.setVisible(false);
        });
        JButton openButton = new JButton("Open an Existing Resume");
        openButton.addActionListener(evt -> {
            LOGGER.info("Opening existing resume.");
            to.us.resume_builder.business.util.FileDialog fileDialog = new FileDialog("json", null);
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
            this.setVisible(false);
        });
        JButton guideButton = new JButton("View the User Guide");
        guideButton.addActionListener(evt -> {
            LOGGER.info("Opening user guide.");
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI("http://resume-builder.us.to/userguide.html"));
                } else {
                    JOptionPane.showMessageDialog(this, "Could not open user guide.");
                }
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Could not open user guide.");
            }
        });
        buttons.add(createButton);
        buttons.add(openButton);
        buttons.add(guideButton);
        panel.add(buttons, BorderLayout.CENTER);

        this.add(panel);
        this.setSize(500, 400);
        this.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
