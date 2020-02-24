package to.us.resume_builder.file;

import com.google.gson.*;

import java.io.*;
import java.text.DateFormat;

/**
 * This class allows for importing and exporting of ResumeFiles.
 *
 * @author Jacob
 */
public class ResumeFileManager {
    // Used for marshalling/unmarshalling json
    private static Gson gson = new GsonBuilder()
        .serializeNulls()
        .setDateFormat(DateFormat.FULL)
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setPrettyPrinting()
        .create();

    /**
     * Imports a given resume data JSON file.
     *
     * @param path The path to the file to be imported from.
     * @return The ResumeFile read from the file.
     * @throws IOException
     */
    public static ResumeFile importFile(String path) throws IOException {
        Reader file = new FileReader(path);
        ResumeFile r = null;
        try {
            r = gson.fromJson(file, ResumeFile.class);
        } catch(JsonSyntaxException e) {
            return null;
        } catch(JsonIOException e) {
            return null;
        } finally {
            file.close();
        }
        return r;
    }

    /**
     * Exports a given ResumeFile to the requested file path.
     *
     * @param r The ResumeFile to be exported
     * @param path The path to the file to export to.
     * @throws IOException
     */
    public static void exportFile(ResumeFile r, String path) throws IOException {
        String json = gson.toJson(r);
        Writer file = new FileWriter(path);
        file.write(json);
        file.close();
    }

}
