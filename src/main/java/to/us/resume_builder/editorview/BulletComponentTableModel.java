package to.us.resume_builder.editorview;

import to.us.resume_builder.resume_components.Bullet;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.List;
import java.util.Vector;

public class BulletComponentTableModel extends AbstractTableModel implements TableModel {

    List<Bullet> data;
    String[] columnNames;

    /**
     * Creates a TableModel for a BulletComponent
     * @param data
     * @param columnNames
     */
    public BulletComponentTableModel(List<Bullet> data, String[] columnNames) {
        this.columnNames = columnNames;
        this.data = data;
    }

    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    @Override
    public int getRowCount() {
        return data.size();
    }

    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Returns a default name for the column using spreadsheet conventions:
     * A, B, C, ... Z, AA, AB, etc.  If <code>column</code> cannot be found,
     * returns an empty string.
     *
     * @param column the column being queried
     * @return a string containing the default name of <code>column</code>
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * Returns <code>Object.class</code> regardless of <code>columnIndex</code>.
     *
     * @param columnIndex the column being queried
     * @return the Object.class
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return Boolean.class;
            case 1:
                return String.class;
        }
        return null;
    }

    /**
     * Returns false.  This is the default implementation for all cells.
     *
     * @param rowIndex    the row being queried
     * @param columnIndex the column being queried
     * @return false
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param rowIndex    the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     * @return the value Object at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return data.get(rowIndex).getVisible();
            case 1:
                return data.get(rowIndex).getText();
        }
        return null;
    }

    /**
     * This empty implementation is provided so users don't have to implement
     * this method if their data model is not editable.
     *
     * @param aValue      value to assign to cell
     * @param rowIndex    row of cell
     * @param columnIndex column of cell
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                data.get(rowIndex).setVisible((Boolean) aValue);
                break;
            case 1:
                data.get(rowIndex).setText((String) aValue);
                break;
        }
    }
}

