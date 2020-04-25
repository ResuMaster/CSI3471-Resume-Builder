package to.us.resume_builder.business.resume_file_management;

import com.google.gson.*;
import to.us.resume_builder.data.resume_components.category.Category;

import java.io.*;
import java.text.DateFormat;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * This class allows for importing and exporting of ResumeFiles.
 *
 * @author Jacob Curtis
 */
public class ResumeFileManager {
    private static Logger LOG = Logger.getLogger(ResumeFileManager.class.getName());

    /**
     * A static Gson instance to marshal/unmarshal JSON
     */
    private static Gson gson = new GsonBuilder()
        .serializeNulls()
        .registerTypeAdapter(Category.class, new CategoryDeserializer())
        .registerTypeHierarchyAdapter(Collection.class, new ListSerializer())
        .setDateFormat(DateFormat.FULL)
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setPrettyPrinting()
        .create();

    /**
     * Imports a given resume data JSON file.
     *
     * @param path The path to the file to be imported from.
     * @return The ResumeFile read from the file.
     * @throws IOException exception opening, closing or reading from json file
     */
    public static ResumeFile importFile(String path) throws IOException {
        LOG.info("Importing resume data file from " + path);
        Reader file = new FileReader(path);
        ResumeFile r = null;
        try {
            LOG.info("Attempting to deserialize ResumeFile");
            r = gson.fromJson(file, ResumeFile.class);
        } catch (JsonSyntaxException | JsonIOException e) {
            LOG.warning("ResumeFile deserializaiton failed: " + e);
            return null;
        } finally {
            file.close();
        }
        LOG.info("ResumeFile was successfully deserialized.");
        r.setFilePath(path);
        return r;
    }

    /**
     * Exports a given ResumeFile to the requested file path.
     *
     * @param r The ResumeFile to be exported
     * @param path The path to the file to export to.
     * @throws IOException exception creating, closing or writing to json file
     */
    public static void exportFile(ResumeFile r, String path) throws IOException {
        LOG.info("Serializing ResumeFile for export");
        String json = gson.toJson(r);
        Writer file = new FileWriter(path);
        LOG.info("Attempting to write ResumeFile JSON to " + path);
        file.write(json);
        file.close();
    }

}
