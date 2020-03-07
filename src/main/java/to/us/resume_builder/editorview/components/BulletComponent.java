package to.us.resume_builder.editorview.components;

import to.us.resume_builder.editorview.BulletComponentTableModel;
import to.us.resume_builder.editorview.MultilineCellEditor;
import to.us.resume_builder.editorview.MultilineCellRenderer;
import to.us.resume_builder.resume_components.Bullet;
import to.us.resume_builder.resume_components.IBulletContainer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class BulletComponent extends JPanel {
    private JTable table;
    private List<Bullet> bulletList;
    private List<Bullet> ref;

    /**
     * Creates a bullet component that is used in in BulletCategory and
     * ExperienceComponent.
     *
     * @param bullets The list of bullets to fill the table with.
     */
    public BulletComponent(List<Bullet> bullets) {
        super(new BorderLayout());

        String[] columnNames = { "Visible", "Text" };

        bulletList = new ArrayList<>(bullets);
        ref = bullets;

        table = new JTable(new BulletComponentTableModel(bulletList, columnNames));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);

        table.getColumnModel().getColumn(0).setMaxWidth(45);

        int lines = 2;
        table.setRowHeight(table.getRowHeight() * lines);
        table.setDefaultRenderer(String.class, new MultilineCellRenderer());
        table.setDefaultEditor(String.class, new MultilineCellEditor());
        table.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        this.setPreferredSize(new Dimension(200, 150));
        this.setBackground(Color.RED.brighter().brighter().brighter());
    }

    public void save() {
        ref.clear();
        ref.addAll(bulletList);
    }
}
