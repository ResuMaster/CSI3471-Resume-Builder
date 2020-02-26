package to.us.resume_builder.editorview;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.Vector;

public class BulletComponentTableModel extends AbstractTableModel implements TableModel {

    Vector<Object[]> data;
    String[] columnNames;

    public BulletComponentTableModel(Vector<Object[]> data, String[] columnNames) {
        super();
        this.columnNames = columnNames;
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return data.get(columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
    }
}

