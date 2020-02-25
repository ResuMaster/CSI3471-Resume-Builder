package to.us.resume_builder.export;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Micah Schiewe
 */
public class LatexCompiler {
    // Commands for all OSs (OSes? OS's? Plural of OS)
    private static final String[] COMPILE_COMMAND = { "pdflatex ", "pdflatex ", "pdflatex " };

    // Extensions used
    private static final String MAIN = ".tex";


    // Commands for the current OS
    private final String compile;

    /*
     * Create a LatexCompiler pre-loaded with the commands to compile/delete on the
     * current OS. Currently unclear if this architecture will be needed.
     */
    public LatexCompiler() {
        // Dependency currently unneeded
        // int os = OSIdentifier.OS.index;
        // compile = COMPILE_COMMAND[os];
        compile = COMPILE_COMMAND[0];
    }

    /*
     * Compile the file given by the listed absolute filename
     */
    public int compile(Path filePath) {
        // Temporary artifacts
        final String[] ARTIFACTS_TO_DELETE = { ".aux", ".log", ".tex" };

        // Attempt to generate
        try {
            int retVal = Runtime.getRuntime().exec("pdflatex \"" + filePath.toAbsolutePath().toString() + "\"", null, filePath.getParent().toFile()).waitFor();

            // Clean up artifacts
            for (String extension : ARTIFACTS_TO_DELETE) {
                Files.deleteIfExists(filePath.resolveSibling(filePath.getFileName().toString().split("\\." ,1)[0] + "." + extension));
            }

            return retVal;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Integer.MIN_VALUE;
        }
    }

    /*
     * Wraps the given String in quotes
     */
    private String wrap(String... strings) {
        StringBuilder sb = new StringBuilder();
        sb.append('\"');
        for (String s : strings)
            sb.append(s);
        sb.append('\"');

        return sb.toString();
    }
}