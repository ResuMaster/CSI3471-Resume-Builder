package to.us.resume_builder.business.resume_file_management;

import com.google.gson.*;
import to.us.resume_builder.data.resume_components.category.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to resolve deserializing the abstract category class from JSON.
 *
 * @author Jacob Curtis
 */
public class CategoryDeserializer implements JsonDeserializer<Category> {
    /**
     * A map containing the different Category types for their corresponding
     * string key.
     */
    static private Map<String, Class<?>> typeToClass = new HashMap<>();

    static {
        typeToClass.put("HEADER", HeaderCategory.class);
        typeToClass.put("TEXT", TextCategory.class);
        typeToClass.put("EXPERIENCE", ExperienceCategory.class);
        typeToClass.put("BULLETS", BulletCategory.class);
    }

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
        String categoryType = jsonObject.get("type").getAsString();
        if (!typeToClass.containsKey(categoryType)) {
            throw new JsonParseException("Invalid Category Type");
        }
        return jsonDeserializationContext.deserialize(jsonElement, typeToClass.get(categoryType));
    }
}
