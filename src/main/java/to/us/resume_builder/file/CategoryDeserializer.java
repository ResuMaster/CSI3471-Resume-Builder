package to.us.resume_builder.file;

import com.google.gson.*;
import to.us.resume_builder.resume_components.category.*;

import java.lang.reflect.Type;

/**
 * Class to resolve deserializing the abstract category class from JSON.
 *
 * @author Jacob
 */
public class CategoryDeserializer implements JsonDeserializer<Category> {
    /**
     * Fulfills the JsonDeserializer interface to deserialize Category.
     *
     * @param jsonElement the json element to be deserialized
     * @param type the type of the json element
     * @param jsonDeserializationContext the deserialization context
     * @return the deserialized category
     * @throws JsonParseException if there was an invalid category type or if the called deserialize method throws it
     */
    @Override
    public Category deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Class<?> c = null;
        switch(jsonObject.get("type").getAsString()) {
            case "HEADER": {
                c = HeaderCategory.class;
                break;
            }
            case "TEXT": {
                c = TextCategory.class;
                break;
            }
            case "EXPERIENCE": {
                c = ExperienceCategory.class;
                break;
            }
            case "BULLETS": {
                c = BulletCategory.class;
                break;
            }
            default: {
                throw new JsonParseException("Invalid Category Type");
            }
        }
        return jsonDeserializationContext.deserialize(jsonElement, c);
    }
}
