package to.us.resume_builder.editorview.components;

import to.us.resume_builder.editorview.BulletComponentTableModel;
import to.us.resume_builder.resume_components.Bullet;
import to.us.resume_builder.resume_components.IBulletContainer;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class BulletComponent extends JPanel {
    private JTable table;
    private List<Bullet> bulletList;

    /**
     * Creates a bullet component that
     *
     * @param bullets
     */
    public BulletComponent(List<Bullet> bullets) {
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        String[] columnNames = { "Visible", "Text" };

        bulletList = bullets;

        table = new JTable(new BulletComponentTableModel(bulletList, columnNames));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);

        c.fill = GridBagConstraints.HORIZONTAL;
        add(scrollPane);

        this.setPreferredSize(new Dimension(200,200));
    }

    public void save() {

    }
}
