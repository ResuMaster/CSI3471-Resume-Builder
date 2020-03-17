package to.us.resume_builder.editor_view.components;

import to.us.resume_builder.resume_components.Bullet;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

//TODO REDO constructor to take an IBulletContainer(add and remove need id generation)
public class BulletComponent extends JPanel {
    private JTable table;
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

        ref = bullets;

        table = new JTable(new BulletComponentTableModel(new ArrayList<>(bullets), columnNames));
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

        JPanel buttonGroup = new JPanel();
        buttonGroup.setLayout(new BoxLayout(buttonGroup, BoxLayout.LINE_AXIS));
        buttonGroup.add(Box.createHorizontalGlue());
        JButton add = new JButton("Add Bullet");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((BulletComponentTableModel) table.getModel()).addBullet();
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        });
        buttonGroup.add(add);

        JButton remove = new JButton("Remove Bullet");
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = table.getSelectedRow();
                ((BulletComponentTableModel) table.getModel()).removeBullet(index);
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        });
        buttonGroup.add(remove);

        JButton moveUp = new JButton("Move Selected Up");
        moveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = table.getSelectedRow();
                if (index != -1 && index != 0) {
                    ((BulletComponentTableModel) table.getModel()).moveUp(index);
                    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                    table.setRowSelectionInterval(index - 1, index - 1);
                }
            }
        });
        buttonGroup.add(moveUp);

        JButton moveDown = new JButton("Move Selected Down");
        moveDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = table.getSelectedRow();
                System.out.println(index);
                if (index != -1 && index + 1 != table.getRowCount()) {
                    ((BulletComponentTableModel) table.getModel()).moveDown(index);
                    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                    table.setRowSelectionInterval(index + 1, index + 1);
                }
            }
        });
        buttonGroup.add(moveDown);

        buttonGroup.add(Box.createHorizontalGlue());
        this.add(buttonGroup, BorderLayout.PAGE_START);

        this.add(scrollPane, BorderLayout.CENTER);

        this.setPreferredSize(new Dimension(200, 175));
        this.setBackground(Color.RED.brighter().brighter().brighter());
    }

    public void save() {
        ref.clear();
        ref.addAll(((BulletComponentTableModel) table.getModel()).data);
    }
}
