package to.us.resume_builder.presentation.components;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * This class allows a JTable cell to contain a two-row text editor.
 *
 * @author Matthew McCaskill
 */
public class MultilineCellEditor extends AbstractCellEditor implements TableCellEditor {
    /**
     * The {@link JTextArea} used to edit the text values.
     */
    private JTextArea component = new JTextArea();

    /**
     * Constructs a <code>MultilineCellEditor</code>.
     */
    public MultilineCellEditor() {
        // Setup the JTextArea options
        component.setLineWrap(true);
        component.setWrapStyleWord(true);
        component.setOpaque(true);
        component.setRows(2);
        component.setBorder(BorderFactory.createLineBorder(Color.black));

        // Add a key listener to stop editing on ENTER/RETURN
        component.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    MultilineCellEditor.this.stopCellEditing();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    MultilineCellEditor.this.stopCellEditing();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    MultilineCellEditor.this.stopCellEditing();
                }
            }
        });
    }

    /**
     * Sets an initial <code>value</code> for the editor.  This will cause the
     * editor to <code>stopEditing</code> and lose any partially edited value if
     * the editor is editing when this method is called.
     *
     * Returns the component that should be added to the client's
     * <code>Component</code> hierarchy.  Once installed in the client's
     * hierarchy this component will then be able to draw and receive user
     * input.
     *
     * @param table      The <code>JTable</code> that is asking the editor to
     *                   edit; can be <code>null</code>.
     * @param value      The value of the cell to be edited; it is up to the
     *                   specific editor to interpret and draw the value.  For
     *                   example, if value is the string "true", it could be
     *                   rendered as a string or it could be rendered as a check
     *                   box that is checked.  <code>null</code> is a valid
     *                   value.
     * @param isSelected True if the cell is to be rendered with highlighting.
     * @param row        The row of the cell being edited.
     * @param column     The column of the cell being edited.
     *
     * @return The component for editing.
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        component.setForeground(table.getForeground());
        component.setBackground(table.getBackground());
        component.setText((String) value);
        component.setFont(table.getFont());

        return component;
    }

    /**
     * Get the value of the cell editor.
     *
     * @return The value of the cell editor.
     */
    @Override
    public Object getCellEditorValue() {
        return component.getText();
    }

    /**
     * Get the edit state of the cell. Returns true if the cell was
     * double-clicked.
     *
     * @param e The event object that triggered the edit.
     *
     * @return Whether or not the cell was double-clicked.
     */
    @Override
    public boolean isCellEditable(EventObject e) {
        if (super.isCellEditable(e)) {
            if (e instanceof MouseEvent) {
                MouseEvent me = (MouseEvent) e;

                return me.getClickCount() >= 2;
            }
        }
        return false;
    }
}