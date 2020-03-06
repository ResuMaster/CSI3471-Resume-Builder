package to.us.resume_builder.editorview.components;

import to.us.resume_builder.editorview.BulletComponentTableModel;
import to.us.resume_builder.resume_components.Bullet;

import javax.swing.*;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class BulletComponent extends JPanel {
    private JTable table;
    private List<Bullet> bulletList;

    /**
     * Creates a bullet component that
     *
     * @param bullets
     */
    public BulletComponent(List<Bullet> bullets) {
//        String[] columnNames = { "Visible", "Text" };
//        Vector<Object[]> data = bullets.stream().map(b -> new Object[] { b.getVisible(), b.getText() }).collect(Collectors.toCollection(Vector::new));
//
//        table = new JTable(new BulletComponentTableModel(data, columnNames));
//        table.getTableHeader().setResizingAllowed(false);
//        table.getTableHeader().setReorderingAllowed(false);
//
//        JScrollPane scrollPane = new JScrollPane(table);
//
//        add(scrollPane);
    }

    public void save() {

    }
}
