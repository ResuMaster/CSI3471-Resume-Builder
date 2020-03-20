package to.us.resume_builder.file;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Class to resolve serializing empty lists to JSON.
 *
 * @author Jacob Curtis
 */
class ListSerializer implements JsonSerializer<List<?>> {
    /**
     * Fulfills the JsonSerializer interface to deserialize Category.
     *
     * @param objects the list to be serialized
     * @param type the type of the json element
     * @param jsonSerializationContext the serialization context
     * @return the serialized list
     */
    @Override
    public JsonElement serialize(List<?> objects, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray array = new JsonArray();
        if (objects == null) {
            return array;
        }

        for (Object child : objects) {
            JsonElement element = jsonSerializationContext.serialize(child);
            array.add(element);
        }

        return array;
    }
}