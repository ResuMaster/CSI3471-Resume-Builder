package to.us.resume_builder.editorview;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EditorCategorySelector extends JPanel implements ListSelectionListener {
    private JList<String> categories;
    private JScrollPane scroll;

    public EditorCategorySelector(JList<String> jList) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        categories = jList;
        scroll = new JScrollPane();

        categories.addListSelectionListener(this);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    // TODO implement
                    int index = categories.locationToIndex(e.getPoint());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO implement
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO implement
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO implement
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO implement
            }
        });

        scroll.add(categories);
        scroll.getViewport().setView(categories);
        add(scroll);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
