package to.us.resume_builder.editor_view.components;

import to.us.resume_builder.resume_components.Bullet;
import to.us.resume_builder.resume_components.IBulletContainer;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Ashley Lu Couch
 */
public class BulletListEditorTableModel extends AbstractTableModel implements TableModel {

    List<Bullet> data;
    String[] columnNames;
    IBulletContainer bulletC;

    public void addBullet(){
//        data.add(bulletC.getBulletByID(bulletC.addBullet()));
        data.add(new Bullet("YYY"));
    }

    public void removeBullet(int index){
        data.remove(index);
    }

    /**
     * Move a given bullet up one position.
     *
     * @param index The index of the one to move up.
     */
    public void moveUp(int index) {
        Collections.swap(data, index, index - 1);
    }

    /**
     * Move a given bullet down one position.
     *
     * @param index The index of the one to move down.
     */
    public void moveDown(int index) {
        Collections.swap(data, index, index + 1);
    }

    /**
     * Creates a TableModel for a BulletComponent.
     *
     * @param data        The data to fill the table with.
     * @param columnNames The names for the columns.
     */
    public BulletListEditorTableModel(List<Bullet> data, String[] columnNames, IBulletContainer bulletC) {
        this.columnNames = columnNames;
        this.data = data;
        this.bulletC = bulletC;
    }

    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it is called frequently
     * during rendering.
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
     * Returns a default name for the column using spreadsheet conventions: A,
     * B, C, ... Z, AA, AB, etc.  If <code>column</code> cannot be found,
     * returns an empty string.
     *
     * @param column the column being queried
     *
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
     *
     * @return the Object.class
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
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
     *
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
     *
     * @return the value Object at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
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
        switch (columnIndex) {
            case 0:
                data.get(rowIndex).setVisible((Boolean) aValue);
                break;
            case 1:
                data.get(rowIndex).setText((String) aValue);
                break;
        }
    }
}

