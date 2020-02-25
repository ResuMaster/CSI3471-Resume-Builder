package to.us.resume_builder;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class enables the use of a configuration file with the program.
 */
public class ApplicationConfiguration {
    private static ApplicationConfiguration instance = null;
    private Map<String, Object> configuration;

    /**
     * Construct the <code>ApplicationConfiguration</code> singleton instance.
     */
    public ApplicationConfiguration() {
        // Read the configuration file
        Gson gson = new Gson();
        try {
            configuration = gson.fromJson(new BufferedReader(new FileReader("config.json")), Map.class);
        } catch (IOException e) {
            configuration = new HashMap<>();
            System.err.println("Could not read configuration file.");
        }

        // Setup default options for any unspecified options
        setDefaults();
    }

    /**
     * Set the default options for any options not specified.
     */
    private void setDefaults() {
        setIfNotPresent("templates.directory", "./templates/");
        setIfNotPresent("export.tempLocation", "./temp/");
    }

    /**
     * Set a key-value pair only if the key is not already in the config map.
     *
     * @param key   The key to set.
     * @param value The value to set at the key.
     */
    private void setIfNotPresent(String key, Object value) {
        if (!configuration.containsKey(key)) {
            configuration.put(key, value);
        }
    }

    /**
     * Get a string value from the configuration map.
     *
     * @param key The key to query for.
     *
     * @return The value at <code>key</code> (if the value is a {@link String})
     *     or a string representation of it (if not). If the key does not exist
     *     in the map, this returns <code>null</code>.
     */
    public String getString(String key) {
        if (configuration.containsKey(key)) {
            return configuration.get(key).toString();
        } else {
            return null;
        }
    }

    /**
     * Get the current instance of the singleton.
     *
     * @return The current instance of the singleton.
     */
    public static ApplicationConfiguration getInstance() {
        if (instance == null)
            instance = new ApplicationConfiguration();

        return instance;
    }
}
