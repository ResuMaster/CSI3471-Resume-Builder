package to.us.resume_builder.business.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Flyweight factory for the SimpleImagePanel class
 * <p>
 * Example of Design Pattern: Flyweight (the flyweights themselves being the
 * Java Image class)
 * 
 * @author Micah
 */
public class ImageCache {
    /**
     * Logs when an Image is being loaded
     */
    private static final Logger LOG = Logger.getLogger(ImageCache.class.getName());

    /**
     * The only instance of an ImageCache
     */
    private static ImageCache singleton;

    /**
     * An Object to ensure only a single instance of ImageCache is created by using a synchronization
     */
    private static final Object LOCK = new Object();

    /** The cache of images, used to save memory */
    private Map<String, Image> images;

    /**
     * Allows access to the singleton instance.
     * 
     * @return The singleton ImageFactory.
     */
    public static ImageCache getInstance() {
        if (singleton == null) {
            synchronized (LOCK) {
                if (singleton == null)
                    singleton = new ImageCache();
            }
        }
        return singleton;
    }

    /**
     * Initializes a HashMap to hold the locations of the various images to be cached
     */
    private ImageCache() {
        images = new HashMap<>();
    }

    /**
     * Allows access to all cached images based on filename.
     * 
     * @param path The path to the image
     * @return The flyweight Image
     */
    public Image getImage(String path) {
        if (!images.containsKey(path)) {
            LOG.info("Loading image " + path);
            images.put(path, loadImage(path));
        }
        return images.get(path);
    }

    /**
     * Loads a local image from inside of the JAR.
     *
     * @param pathAndFileName Path to the image file to load.
     *
     * @return A corresponding {@link Image} instance.
     * 
     * @author Matthew McCaskill
     */
    protected Image loadImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }
}
