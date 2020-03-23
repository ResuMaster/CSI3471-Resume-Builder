package to.us.resume_builder.editor_view.components;

import to.us.resume_builder.resume_components.Bullet;
import to.us.resume_builder.resume_components.IBulletContainer;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.Collections;
import java.util.List;

/**
 * Custom table model for a list of Bullets
 *
 * @author Ashley Lu Couch
 */
public class BulletComponentTableModel extends AbstractTableModel implements TableModel {
    /**
     * A list of each bullet to be displayed in the TableModel
     */
    List<Bullet> data;
    /**
     * An array of Strings indicating each column names
     */
    String[] columnNames;
    /**
     * An object which holds the Bullets of a Bullet Category or Experience Component
     */
    IBulletContainer bulletC;

    /**
     * Adds a new Bullet ID to data initialized blank
     */
    public void addBullet(){
//        data.add(bulletC.getBulletByID(bulletC.addBullet()));
        data.add(new Bullet("YYY"));
    }

    /**
     * Removes a Bullet from data by the index in the Bullet list
     * @param index the index of the Bullet to remove
     */
    public void removeBullet(int index){
        data.remove(index);
    }

    /**
     * Move a given bullet up one position.
     *
     * @param index The index of the Bullet to move up.
     */
    public void moveUp(int index) {
        Collections.swap(data, index, index - 1);
    }

    /**
     * Move a given bullet down one position.
     *
     * @param index The index of the Bullet to move down.
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
    public BulletComponentTableModel(List<Bullet> data, String[] columnNames, IBulletContainer bulletC) {
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
     * Returns the column name from the column names array
     *
     * @param column the column being queried
     *
     * @return a string containing the name of <code>column</code>
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * Returns <code>Boolean.class</code> or <code>String.class</code> if the
     * index is 0 or 1 respectively.
     *
     * @param columnIndex the column being queried
     *
     * @return Boolean if columnIndex = 0 or String if columnIndex = 1
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
     * Returns true so that all cells are editable
     *
     * @param rowIndex    the row being queried
     * @param columnIndex the column being queried
     *
     * @return true
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

