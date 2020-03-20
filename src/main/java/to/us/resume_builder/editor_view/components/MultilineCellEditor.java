package to.us.resume_builder.editor_view.components;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 *
 * @author Matthew McCaskill
 */
public class MultilineCellEditor extends AbstractCellEditor implements TableCellEditor {
    JTextArea component = new JTextArea();

    public MultilineCellEditor() {
        component.setLineWrap(true);
        component.setWrapStyleWord(true);
        component.setOpaque(true);
        component.setRows(2);
        component.setBorder(BorderFactory.createLineBorder(Color.black));

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

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex) {
        component.setForeground(table.getForeground());
        component.setBackground(table.getBackground());
        component.setText((String) value);
        component.setFont(table.getFont());

        return component;
    }

    @Override
    public Object getCellEditorValue() {
        return component.getText();
    }

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