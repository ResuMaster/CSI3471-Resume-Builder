package to.us.resume_builder.presentation.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.AbstractTableModel;

import to.us.resume_builder.data.resume_components.Bullet;
import to.us.resume_builder.data.resume_components.IBulletContainer;

/**
 * Custom table model for a list of Bullets
 *
 * @author Ashley Lu Couch
 */
public class BulletListEditorTableModel extends AbstractTableModel {
    /** SerialUID, valid as of Iteration 3 of development (4/30/2020) */
    private static final long serialVersionUID = 8917865735485157487L;

    /**
     * Logs moving, adding, and removing Bullets
     */
    private static final Logger LOG = Logger.getLogger(BulletListEditorTableModel.class.getName());

    /**
     * A list of each bullet to be displayed in the TableModel
     */
    transient List<Bullet> data;
    /**
     * An array of Strings indicating each column names
     */
    String[] columnNames;
    /**
     * An object which holds the Bullets of a Bullet Category or Experience
     * Component
     */
    IBulletContainer bulletC;

    /**
     * Adds a new Bullet ID to data initialized blank
     */
    public void addBullet() {
        LOG.logp(Level.INFO, BulletListEditorTableModel.class.getName(), "addBullet", "adding new bullet");
        data.add(new Bullet("YYY"));
    }

    /**
     * Removes a Bullet from data by the index in the Bullet list
     *
     * @param index the index of the Bullet to remove
     */
    public void removeBullet(int index) {
        LOG.logp(Level.INFO, BulletListEditorTableModel.class.getName(), "removeBullet", "removing bullet index " + index);
        data.remove(index);
    }

    /**
     * Move a given bullet up one position.
     *
     * @param index The index of the Bullet to move up.
     */
    public void moveUp(int index) {
        LOG.logp(Level.INFO, BulletListEditorTableModel.class.getName(), "moveUp", "shifting bullet index " + index + " up by one");
        Collections.swap(data, index, index - 1);
    }

    /**
     * Move a given bullet down one position.
     *
     * @param index The index of the Bullet to move down.
     */
    public void moveDown(int index) {
        LOG.logp(Level.INFO, BulletListEditorTableModel.class.getName(), "moveDown", "shifting bullet index " + index + " down by one");
        Collections.swap(data, index, index + 1);
    }

    /**
     * Creates a TableModel for a BulletComponent.
     *
     * @param data        The data to fill the table with.
     * @param columnNames The names for the columns.
     * @param bulletC     The IBulletContainer being edited
     */
    public BulletListEditorTableModel(List<Bullet> data, final String[] columnNames, IBulletContainer bulletC) {
        this.columnNames = columnNames;
        this.bulletC = bulletC;
        
        // Copy column names
        this.columnNames = Arrays.copyOf(columnNames, columnNames.length);

        // Copy data
        this.data = new ArrayList<>();
        for (Bullet b : data)
            this.data.add(b.clone());
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
            default:
                LOG.warning(String.format("Attempted to write %s to undefined column %d at row %d", aValue, columnIndex, rowIndex));
                break;
        }
    }
}

