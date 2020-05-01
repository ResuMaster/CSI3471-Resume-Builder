package to.us.resume_builder.business.server_connect.fileio_response;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * A Singleton responsible for sending responses from FilIO's upload request to Gson
 * @author Micah Schiewe
 */
public class FileIOResponseParser {
	/** Logs a parseResponse request */
	private static final Logger LOG = Logger.getLogger(FileIOResponseParser.class.getName());

	/** Singleton instance of a FileIOParser */
	private static FileIOResponseParser manager;
	/** Object which controls the creation of one and only one FileIOResponseParser */
	private static final Object LOCK = new Object();

	/** The Gson instance used to parse the response. */
	private final Gson gson;

	/**
	 * Get the parser for file.io's responses.
	 * 
	 * @return The singleton parser.
	 */
	public static FileIOResponseParser getManager() {
		if (manager == null) {
			synchronized (LOCK) {
				if (manager == null)
					manager = new FileIOResponseParser();
			}
		}
		return manager;
	}

	/**
	 * Parse the given response from file.io.
	 * 
	 * @param response file.io's response to an upload request.
	 * @return The response, as an object, or null if the given response was
	 *         invalid.
	 */
	public FileIOResponse parseResponse(String response) {
		try {
			return gson.fromJson(response, FileIOResponse.class);
		} catch (JsonSyntaxException error) {
			LOG.logp(Level.WARNING, getClass().getName(), "parseResponse",
					"The given response \"" + response + "\" was invalid.", error);
			return null;
		}
	}

	/**
	 * Init the singleton FileIOResponseManager.
	 */
	private FileIOResponseParser() {
		gson = new GsonBuilder().create();
	}
}
