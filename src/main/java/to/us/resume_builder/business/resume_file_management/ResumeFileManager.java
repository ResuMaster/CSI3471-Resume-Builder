package to.us.resume_builder.business.resume_file_management;

import com.google.gson.*;
import to.us.resume_builder.data.resume_components.category.Category;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * This class allows for importing and exporting of ResumeFiles.
 *
 * @author Jacob Curtis
 */
public class ResumeFileManager {
    /** Logs exporting and importing the JSON file */
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
        ResumeFile file = importFile(new BufferedReader(new FileReader(path, StandardCharsets.UTF_8)));
        if (file != null) {
            file.setFilePath(path);
        }
        return file;
    }

    /**
     * Imports a given resume data JSON reader.
     *
     * @param reader The reader containing the JSON data to be imported.
     * @return The ResumeFile read from the file.
     * @throws IOException exception opening, closing or reading from json file
     */
    public static ResumeFile importFile(Reader reader) throws IOException {
        ResumeFile r = null;
        try {
            LOG.info("Attempting to deserialize ResumeFile");
            r = gson.fromJson(reader, ResumeFile.class);
        } catch (JsonSyntaxException | JsonIOException e) {
            LOG.warning("ResumeFile deserializaiton failed: " + e);
            return null;
        } finally {
            reader.close();
        }
        LOG.info("ResumeFile was successfully deserialized.");
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
        exportFile(r, new BufferedWriter(new FileWriter(path, StandardCharsets.UTF_8)));
    }

    /**
     * Exports a given ResumeFile to the requested Writer.
     *
     * @param r The ResumeFile to be exported
     * @param writer The path to the file to export to.
     * @throws IOException exception creating, closing or writing to json file
     */
    public static void exportFile(ResumeFile r, Writer writer) throws IOException {
        String json = gson.toJson(r);
        writer.write(json);
        writer.close();
    }

}
