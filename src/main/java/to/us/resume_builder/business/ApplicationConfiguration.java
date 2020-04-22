package to.us.resume_builder.business;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        boolean createConfigFile = false;
        try {
            Gson gson = new Gson();
            configuration = gson.fromJson(new BufferedReader(new FileReader("config.json", StandardCharsets.UTF_8)), Map.class);
        } catch (FileNotFoundException e) {
            configuration = new HashMap<>();
            System.out.println("Configuration file does not exist. Creating...");
            createConfigFile = true;
        } catch (IOException e) {
            configuration = new HashMap<>();
            System.err.println("Could not read configuration file.");

        }

        // Setup default options for any unspecified options
        setDefaults();

        // Create the default configuration file if needed
        //if (createConfigFile) {
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(configuration);
                Files.writeString(Paths.get("config.json"), json, StandardCharsets.UTF_8);
            } catch (IOException e) {
                System.err.println("Could not write to configuration file.");
            }
        //}
    }

    /**
     * Set the default options for any options not specified.
     */
    private void setDefaults() {
        setIfNotPresent("templates.directory", "./templates/");
        setIfNotPresent("export.tempLocation", "./temp/");
        setIfNotPresent("export.timeout", 60L);
        setIfNotPresent("theme.color", "light");
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
     * Get a long value from the configuration map.
     *
     * @param key The key to query for.
     *
     * @return The value at <code>key</code>. If the key does not exist in the
     *     map, this returns <code>null</code>.
     */
    public Long getLong(String key) {
        try {
            return (long) Double.parseDouble(configuration.get(key).toString());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get the current instance of the singleton.
     *
     * @return The current instance of the singleton.
     */
    public static ApplicationConfiguration getInstance() {
        // Create the instance if it does not already exist
        if (instance == null)
            instance = new ApplicationConfiguration();

        return instance;
    }
}
