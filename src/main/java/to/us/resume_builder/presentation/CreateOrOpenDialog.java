package to.us.resume_builder.presentation;

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
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Prompts user to create or open a {@link Resume}. Also gives the user access to the user guide
 * @author Matthew McCaskill
 */
public class CreateOrOpenDialog extends JFrame {
    /**
     * Logs the user's choice to either create a {@link Resume}, open an existing {@link Resume},
     * or open the user guide.
     */
    private static Logger LOGGER = Logger.getLogger(CreateOrOpenDialog.class.getName());

    /**
     * The top and bottom padding amount for the "ResuMaster" logo
     */
    private static final int LOGO_PADDING = 50;

    /**
     * The Resume which will hold a default {@link HeaderCategory}
     */
    private static final Resume DEFAULT_RESUME = new Resume();

    static {
        String headerID = DEFAULT_RESUME.createCategory(CategoryType.HEADER);
        HeaderCategory header = (HeaderCategory) DEFAULT_RESUME.getCategoryByID(headerID);
        header.setDisplayName("Your Name Here");
        header.setName("Header");
    }

    /**
     * Sets up a dialog with create, open, and view user guide options.
     */
    public CreateOrOpenDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Set up the logo
        SimpleImagePanel logo = new SimpleImagePanel("images/logo.png", 0.4f);
        logo.setBorder(new EmptyBorder(LOGO_PADDING, 0, LOGO_PADDING, 0));
        panel.add(logo, BorderLayout.NORTH);

        // Set up Button panel
        JPanel buttons = new JPanel();
        buttons.setBorder(new EmptyBorder(20, 40, 20, 40));
        GridLayout buttonLayout = new GridLayout(3, 1);
        buttonLayout.setHgap(10);
        buttonLayout.setVgap(40);
        buttons.setLayout(buttonLayout);

        // Set up create Button with action listener
        JButton createButton = new JButton("Create a New Resume");
        createButton.addActionListener(evt -> {
            LOGGER.info("Creating new resume.");
            new EditorFrame(new ResumeFile(DEFAULT_RESUME));
            this.setVisible(false);
        });

        // Set up open Button with action listener
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

        // Set up view user guide button with action listener
        JButton guideButton = new JButton("View the User Guide");
        guideButton.addActionListener(evt -> {
            LOGGER.info("Opening user guide.");
            try {
                Desktop.getDesktop().open(new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("userguide.pdf")).getFile()));
            } catch (IOException e) {
                e.printStackTrace();
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
