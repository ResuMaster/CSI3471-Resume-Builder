package to.us.resume_builder.presentation.components;

import to.us.resume_builder.presentation.IEncapsulatedEditor;
import to.us.resume_builder.data.resume_components.IBulletContainer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Facilitates editing of a Bullet
 * @author Ashley Lu Couch
 * @author Matthew McCaskill
 */
public class BulletListEditor extends JPanel implements IEncapsulatedEditor {
    /**
     * A table which holds each bullet and its visibility
     */
    private JTable table;
    /**
     * A boolean to indicate whether this Bullet Component had been changed
     */
    private boolean modified;
    /**
     * An object which holds the Bullets of a Bullet Category or Experience Component
     */
    private IBulletContainer bulletC;

    /**
     * Creates a bullet component that is used in in BulletCategory and
     * ExperienceComponent.
     *
     * @param bulletContainer The bullet container being edited.
     */
    public BulletListEditor(IBulletContainer bulletContainer) {
        super(new BorderLayout());

        this.modified = false;
        this.bulletC = bulletContainer;

        String[] columnNames = { "Visible", "Text" };

        table = new JTable(new BulletListEditorTableModel(new ArrayList<>(bulletContainer.getBulletList()), columnNames, bulletContainer));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);

        table.getColumnModel().getColumn(0).setMaxWidth(45);

        int lines = 2;
        table.setRowHeight(table.getRowHeight() * lines);
        table.setDefaultRenderer(String.class, new MultilineCellRenderer());
        table.setDefaultEditor(String.class, new MultilineCellEditor());
//        table.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
        scrollPane.setForeground(Color.red);
        scrollPane.getViewport().setBackground(new Color(205, 205, 205));

        // add a button to add a bullet
        JPanel buttonGroup = new JPanel();
        buttonGroup.setLayout(new BoxLayout(buttonGroup, BoxLayout.LINE_AXIS));
        buttonGroup.add(Box.createHorizontalGlue());
        JButton add = new JButton("Add Bullet");
        add.addActionListener(e -> {
            this.modified = true;

            ((BulletListEditorTableModel) table.getModel()).addBullet();
            ((AbstractTableModel) table.getModel()).fireTableDataChanged();
        });
        buttonGroup.add(add);


        // add a button to move a bullet up
        JButton moveUp = new JButton("Move Selected Up");
        moveUp.addActionListener(e -> {
            int index = table.getSelectedRow();
            if (index != -1 && index != 0) {
                this.modified = true;
                ((BulletListEditorTableModel) table.getModel()).moveUp(index);
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                table.setRowSelectionInterval(index - 1, index - 1);
            }
        });
        buttonGroup.add(moveUp);

        // add a button to move a bullet down
        JButton moveDown = new JButton("Move Selected Down");
        moveDown.addActionListener(e -> {
            int index = table.getSelectedRow();
            System.out.println(index);
            if (index != -1 && index + 1 != table.getRowCount()) {
                this.modified = true;
                ((BulletListEditorTableModel) table.getModel()).moveDown(index);
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                table.setRowSelectionInterval(index + 1, index + 1);
            }
        });
        buttonGroup.add(moveDown);

        // add a button to remove a selected bullet
        JButton remove = new JButton("Remove Bullet");
        remove.setBackground(new Color(255, 210, 210, 255));
        remove.addActionListener(e -> {
            int index = table.getSelectedRow();
            if (index != -1) {
                this.modified = true;
                ((BulletListEditorTableModel) table.getModel()).removeBullet(index);
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        });
        buttonGroup.add(remove);

        this.table.getModel().addTableModelListener(e -> BulletListEditor.this.modified = true);

        buttonGroup.add(Box.createHorizontalGlue());
        this.add(buttonGroup, BorderLayout.PAGE_START);

        this.add(scrollPane, BorderLayout.CENTER);

        // To make it not scroll by default
        this.setPreferredSize(new Dimension(650, 200));
    }

    /**
     * Saves changes made in the Bullet UI to the given Bullet
     */
    @Override
    public void save() {
        this.modified = false;

        this.bulletC.getBulletList().clear();
//        this.bulletC.getBulletList().addAll(((BulletComponentTableModel) table.getModel()).data);
        ((BulletListEditorTableModel) this.table.getModel()).data.stream().peek(b -> {
            if (b.getText() == null) b.setText("");
        }).forEach(b -> this.bulletC.addBullet(b));
    }

    /**
     * Returns the status of the modified boolean
     * @return a boolean indicating whether the Bullet Component had been edited
     */
    @Override
    public boolean isModified() {
        return this.modified;
    }
}
