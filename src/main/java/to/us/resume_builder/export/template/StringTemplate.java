package to.us.resume_builder.export.template;

import java.security.InvalidKeyException;
import java.util.Map;

public class StringTemplate {
    private String template;
    private Map<String, String> replacements;

    public StringTemplate() {
        // TODO: populate replacements
    }

    private StringTemplate(StringTemplate other) {
        this.template = other.template;
    }

    public StringTemplate replaceKey(String key, String value) throws Exception { // TODO: InvalidKeyException
        if (this.replacements.containsKey(key)){
            this.replacements.put(key, value);
        }
        else {
            throw new Exception (); // TODO: InvalidKeyException
        }
        return this;
    }

    @Override
    public String toString() {
        return template;
    }

    protected StringTemplate copy()  {
        return new StringTemplate(this);
    }
}
