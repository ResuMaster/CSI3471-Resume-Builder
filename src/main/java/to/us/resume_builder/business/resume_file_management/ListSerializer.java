package to.us.resume_builder.business.resume_file_management;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class to resolve serializing empty lists to JSON.
 *
 * @author Jacob Curtis
 */
class ListSerializer implements JsonSerializer<List<?>> {
    /**
     * Logs when a Serialization occurs
     */
    private static Logger LOG = Logger.getLogger(ListSerializer.class.getName());

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
            LOG.fine("List was null, returning empty JsonArray");
            return array;
        }
        LOG.fine("Serializing " + objects.getClass());
        LOG.fine("Serializing List elements...");
        objects.forEach(child -> {
            JsonElement element = jsonSerializationContext.serialize(child);
            array.add(element);
        });

        return array;
    }
}