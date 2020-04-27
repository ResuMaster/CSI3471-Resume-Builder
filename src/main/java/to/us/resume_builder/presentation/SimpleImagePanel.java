package to.us.resume_builder.presentation;

import to.us.resume_builder.business.util.MiscUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class SimpleImagePanel extends JPanel {
    private static Logger LOGGER = Logger.getLogger(SimpleImagePanel.class.getName());

    private static Map<String, Image> images = new HashMap<>();
    private final Image image;
    private final float scale;

    public SimpleImagePanel(String path) {
        this(path, 1.0f);
    }

    public SimpleImagePanel(String path, float scale) {
        this.scale = scale;

        if (!images.containsKey(path)) {
            LOGGER.info("Loading image " + path);
            images.put(path, MiscUtils.getImage(path));
        }

        Image img = images.get(path);
        image = img.getScaledInstance(img.getWidth(this), img.getHeight(this), Image.SCALE_DEFAULT);

        this.setSize(new Dimension(image.getHeight(this), image.getWidth(this)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int imgWidth =  (int) (image.getWidth(this) * scale);
        int imgHeight = (int) (image.getHeight(this) * scale);

        g.drawImage(image, (this.getWidth() - imgWidth) / 2, (this.getHeight() - imgHeight) / 2, imgWidth, imgHeight, this);
    }
}