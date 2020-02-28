package to.us.resume_builder.editorview;

import javax.swing.*;
import java.util.Vector;

public class BulletComponent extends JPanel {
    private JTable table;

    /**
     * Creates a bullet component that
     * @param bullets
     */
    BulletComponent(Vector<Object[]> bullets) {
        String[] columnNames = { "Visible", "Text" };
        Vector<Object[]> data = bullets;

        table = new JTable(new BulletComponentTableModel(data, columnNames));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);
    }
}
