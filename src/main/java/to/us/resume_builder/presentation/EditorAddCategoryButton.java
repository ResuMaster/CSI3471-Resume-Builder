package to.us.resume_builder.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.ModuleLayer.Controller;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import to.us.resume_builder.data.resume_components.category.Category;
import to.us.resume_builder.business.util.ImageCache;
import to.us.resume_builder.data.resume_components.Resume;
import to.us.resume_builder.data.resume_components.category.CategoryType;
import to.us.resume_builder.presentation.controllers.EditorController;

/**
 * The button which handles requesting a new {@link to.us.resume_builder.data.resume_components.category.Category}
 * be added to the underlying {@link Resume}.
 *
 * @author Micah
 */
public class EditorAddCategoryButton extends JButton {
    /**
     * Logs creation of the category option panel and adding of categories
     */
    private static final Logger LOG = Logger.getLogger(EditorAddCategoryButton.class.getName());

    /**
     * Label for the add {@link Category} button
     */
    private static final String LABEL = "Add Section...";

    /**
     * Message at the top of the {@link Category} choices panel
     */
    private static final String GET_TYPE_MESSAGE = "Select a section type:";

    /**
     * Controller which can add the category to the resume.
     */
    private transient EditorController controller;
    /**
     * Holds the type of Category the user chose
     */
    private CategoryType selectedType;
    /**
     * Labels for the selected Category with a "(Selected)" underneath
     */
    private Map<CategoryType, JLabel> selectedLabels;

    /**
     * Creates a new button ready to pass a request for a new category to its
     * {@link Controller}.
     */
    public EditorAddCategoryButton() {
        super(LABEL);
        addActionListener(e -> addCategory());
        selectedLabels = new HashMap<>();
        selectedType = CategoryType.EXPERIENCE;
    }

    /**
     * Passes a request from the user for a new Category to the controller.
     */
    private void addCategory() {
        CategoryType type = getType();
        LOG.logp(Level.INFO, EditorAddCategoryButton.class.getName(), "addCategory", "adding new category of type " + type);
        if (type != null)
            controller.addCategory(type);
    }

    /**
     * Gets the type of a desired new category from the user.
     *
     * @return The {@link CategoryType} corresponding to the desired new {@link 
     *     to.us.resume_builder.data.resume_components.category.Category}.
     */
    private CategoryType getType() {
        int status = JOptionPane.showConfirmDialog(null, constructDialogContents(), GET_TYPE_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (status == JOptionPane.CANCEL_OPTION || selectedType == null) {
            return null;
        }

        return selectedType;
    }

    /**
     * Constructs a panel with 4 panels, one for each {@link CategoryType}
     * @return A panel containing each {@link CategoryType} with a button for each type
     */
    private JComponent constructDialogContents() {
        LOG.logp(Level.INFO, EditorAddCategoryButton.class.getName(), "constructDialogContents", "creating dialog panel");
        CategoryType[] values = CategoryType.values();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout((values.length + 1) / 2, 2, -1, -1));
        panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        for (CategoryType value : values) {
            panel.add(constructSelectionButton(value));
        }

        return panel;
    }

    /**
     * Constructs a Panel for the designated {@link CategoryType} and listens for any user selections
     * @param type the {@link CategoryType} to create the button for
     * @return a
     */
    private JComponent constructSelectionButton(CategoryType type) {
        LOG.logp(Level.INFO, EditorAddCategoryButton.class.getName(), "constructDialogContents", "creating selection button panel");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        try {
            JLabel wIcon = new JLabel(new ImageIcon(
                    ImageCache.getInstance().getImage("images/" + type.getTemplateFileName() + ".png")));
            wIcon.setSize(100, 100);
            panel.add(wIcon, BorderLayout.WEST);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        JLabel title = new JLabel(type.getName());
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(title.getFont().getStyle() | Font.BOLD));
        infoPanel.add(title, BorderLayout.NORTH);
        JTextArea desc = new JTextArea(type.getDescription());
        desc.setEditable(false);
        JLabel selectedLabel = new JLabel("(Selected)");
        selectedLabel.setForeground(Color.blue);
        selectedLabel.setFont(selectedLabel.getFont().deriveFont(selectedLabel.getFont().getStyle() | Font.BOLD));
        selectedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(selectedLabel, BorderLayout.SOUTH);
        selectedLabels.put(type, selectedLabel);
        infoPanel.add(desc, BorderLayout.CENTER);
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.setBackground(Color.RED);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        clearSelection();

        LOG.logp(Level.INFO, EditorAddCategoryButton.class.getName(), "constructDialogContents", "adding listeners for buttons in the panel");
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                EditorAddCategoryButton.this.selectedType = type;
                super.mouseReleased(e);
                clearSelection();
                selectedLabels.get(type).setText("(Selected)");
                selectedType = type;
            }
        });
        title.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                EditorAddCategoryButton.this.selectedType = type;
                super.mouseReleased(e);
                clearSelection();
                selectedLabels.get(type).setText("(Selected)");
                selectedType = type;
            }
        });
        desc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                EditorAddCategoryButton.this.selectedType = type;
                super.mouseReleased(e);
                clearSelection();
                selectedLabels.get(type).setText("(Selected)");
                selectedType = type;
            }
        });

        return panel;
    }

    /**
     * If the user cancels creating a {@link Category}, remove any "Selected" {@link Category}'s
     */
    private void clearSelection() {
        LOG.logp(Level.INFO, EditorAddCategoryButton.class.getName(), "clearSelection", "clearing the current selection");
        selectedType = null;
        selectedLabels.forEach((key, val) -> {
            val.setText("");
        });
    }

    /**
     * Registers the controller to alert of add category requests.
     *
     * @param e The controller to contact.
     */
    public void setController(EditorController e) {
        controller = e;
    }
}
