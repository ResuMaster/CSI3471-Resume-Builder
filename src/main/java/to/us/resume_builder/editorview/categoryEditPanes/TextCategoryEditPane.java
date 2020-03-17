package to.us.resume_builder.editorview.categoryEditPanes;

import to.us.resume_builder.resume_components.category.HeaderCategory;
import to.us.resume_builder.resume_components.category.TextCategory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextCategoryEditPane extends CategoryEditPane {
    private JTextField fields[];
    private JTextArea text;
    private TextCategory textCategory;


    /**
     * Constructor for a Text Category Edit Pane
     * @param tc the Text Category that is being edited through the edit pane
     */
    public TextCategoryEditPane(TextCategory tc) {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));

        textCategory = tc;
        text = new JTextArea(tc.getText());
        text.setWrapStyleWord(true);
        text.setLineWrap(true);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));

        JScrollPane scrollPane = new JScrollPane(textPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JLabel textLabel = new JLabel("Text: ", SwingConstants.LEFT);

        infoPanel.add(textLabel, BorderLayout.NORTH);
        textPanel.add(text, BorderLayout.NORTH);

        this.add(infoPanel, BorderLayout.NORTH);
//        this.add(scrollPane, BorderLayout.NORTH);
        this.add(textPanel, BorderLayout.NORTH);
    }

    /**
     * Saves all changes made in Text Edit Pane
     */
    @Override
    public void save() {
        textCategory.setText(text.getText());
        textCategory.setName(fields[0].getText());
        textCategory.setDisplayName(fields[1].getText());
    }
}