package to.us.resume_builder.export.template;

import to.us.resume_builder.export.ResumeExportException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class StringTemplate {
    private String template;
    private Map<String, String> replacements;

    public StringTemplate(String template) {
        this.template = template;

        replacements = new HashMap<>();

        Pattern.compile("(?<!\\\\)<([a-z]+)>", Pattern.CASE_INSENSITIVE)
            .matcher(template)
            .results()
            .forEach(r -> replacements.put(r.group(1), null));
    }

    private StringTemplate(StringTemplate other) {
        this.template = other.template;
        this.replacements = new HashMap<>(other.replacements);
    }

    public StringTemplate replaceVariable(String key, String value) {
        if (this.replacements.containsKey(key)) {
            this.replacements.put(key, value);
        }

        return this;
    }

    @Override
    public String toString() {
        String t = template;

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            if (entry.getValue() != null) {
                t = t.replaceAll("<" + entry.getKey() + ">", entry.getValue());
            }
        }

        return t;
    }

    public StringTemplate copy() {
        return new StringTemplate(this);
    }
}
