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
<<<<<<< HEAD
    // Used for marshalling/unmarshalling json
    private static Gson gson = new GsonBuilder()
=======
    Gson gson;

    /**
     * Constructs a ResumeFileManager
     */
    public ResumeFileManager() {
        super();
        gson = new GsonBuilder()
>>>>>>> 6a47fc88421d6d301c1dd11bf2a9d25b34517c2d
            .serializeNulls()
            .setDateFormat(DateFormat.FULL)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .create();
<<<<<<< HEAD
=======
    }
>>>>>>> 6a47fc88421d6d301c1dd11bf2a9d25b34517c2d

    /**
     * Imports a given resume data JSON file.
     *
     * @param path The path to the file to be imported from.
     * @return The ResumeFile read from the file.
     * @throws IOException
     */
<<<<<<< HEAD
    public static ResumeFile importFile(String path) throws IOException {
=======
    public ResumeFile importFile(String path) throws IOException {
>>>>>>> 6a47fc88421d6d301c1dd11bf2a9d25b34517c2d
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
<<<<<<< HEAD
    public static void exportFile(ResumeFile r, String path) throws IOException {
=======
    public void exportFile(ResumeFile r, String path) throws IOException {
>>>>>>> 6a47fc88421d6d301c1dd11bf2a9d25b34517c2d
        String json = gson.toJson(r);
        Writer file = new FileWriter(path);
        file.write(json);
        file.close();
    }

}
