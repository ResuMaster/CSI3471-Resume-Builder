package to.us.resume_builder.business.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class encapsulates string formatting for easier passing of template
 * parameters and arguments.
 *
 * @author Matthew McCaskill
 */
public class StringTemplate implements Cloneable {
    /**
     * The string template with formatting keys in the form.
     */
    private String template;

    /**
     * A {@link Map} of formatting keys to their values, to be replaces in the
     * {@link StringTemplate#toString()} method.
     */
    private Map<String, String> replacements;

    /**
     * Constructs a <code>StringTemplate</code> from a <code>String</code> in
     * with formatting keys in the form of <code>&lt;keyName&gt;</code>.
     *
     * @param template The template <code>String</code>.
     */
    public StringTemplate(String template) {
        this.template = template;

        // Create the replacements map
        replacements = new HashMap<>();

        // Initialize the map of formatting keys
        Pattern.compile("(?<!\\\\)<([a-z]+)>", Pattern.CASE_INSENSITIVE)
            .matcher(template)
            .results()
            .forEach(r -> replacements.put(r.group(1), null));
    }

    /**
     * Constructs a copy of another <code>StringTemplate</code>.
     *
     * @param other The <code>StringTemplate</code> to copy.
     */
    private StringTemplate(StringTemplate other) {
        this.template = other.template;
        this.replacements = new HashMap<>(other.replacements);
    }

    /**
     * Set the value of a key in the template. If the key does not exist in the
     * template, then this method is a no-op.
     *
     * @param key   The key to replace.
     * @param value The value to set the key to.
     *
     * @return A reference to this <code>StringTemplate</code>, for method
     *     chaining.
     */
    public StringTemplate replaceVariable(String key, String value) {
        if (this.replacements.containsKey(key)) {
            this.replacements.put(key, value);
        }

        return this;
    }

    /**
     * Get the string representation of this template, with all keys replaced.
     *
     * @return The string representation of this template.
     */
    @Override
    public String toString() {
        String t = template;

        // Replace each <key> with its value.
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            if (entry.getValue() != null) {
                t = t.replaceAll("<" + entry.getKey() + ">", Matcher.quoteReplacement(entry.getValue()));
            }
        }

        return t;
    }


    /**
     * Get the string representation of this template, with all keys replaced,
     * then run the callback.
     *
     * @param callback A function to call once the string is generated.
     *
     * @return The string representation of this template.
     */
    public String toString(Runnable callback) {
        String t = template;

        // Replace each <key> with its value.
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            if (entry.getValue() != null) {
                t = t.replaceAll("<" + entry.getKey() + ">", Matcher.quoteReplacement(entry.getValue()));
            }
        }

        callback.run();

        return t;
    }

    /**
     * Get a copy of this <code>StringTemplate</code> object.
     *
     * @return A copy of this <code>StringTemplate</code> object.
     */
    @Override
    public StringTemplate clone() {
        StringTemplate clone = null;
        try {
            clone = (StringTemplate) super.clone();
            clone.replacements = new HashMap<>(this.replacements);
        } catch (CloneNotSupportedException cns) {
            System.err.println("Error while cloning StringTemplate");
        }

        return clone;
    }
}
