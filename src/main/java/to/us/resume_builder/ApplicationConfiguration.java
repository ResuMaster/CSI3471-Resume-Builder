package to.us.resume_builder;

import java.util.HashMap;
import java.util.Map;

public class ApplicationConfiguration {
    private static ApplicationConfiguration instance = null;
    private Map<String, String> configuration;

    public ApplicationConfiguration() {
        configuration = new HashMap<>();
        // TODO: read config file

        setDefaults();
    }

    private void setDefaults() {
        configuration.put("templateDirectory", "./src/main/resources/templates/");
    }

    public String get(String key) {
        return configuration.get(key);
    }

    public static ApplicationConfiguration getInstance() {
        if (instance == null)
            instance = new ApplicationConfiguration();

        return instance;
    }
}
