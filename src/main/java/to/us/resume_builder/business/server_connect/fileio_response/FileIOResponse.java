package to.us.resume_builder.business.server_connect.fileio_response;

/**
 * Representation of file.io's response to the user.
 * 
 * @author Micah
 */
public class FileIOResponse {
	/** Success of the call */
	private boolean success;

	/** Key to the file */
	private String key;

	/** Link the file stored at */
	private String link;

	/** Expiry time of the file */
	private String expiry;

	// Error stuff
	/** Error code (if error occurred) */
	private String error;

	/** Error message (if error occured) */
	private String message;

	/**
	 * Gives access to the success/failure of the upload (this also says whether
	 * fields like error and message contain valid data).
	 * 
	 * @return The success of the upload.
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Gives access to the key for the upload.
	 * 
	 * @return The key of the upload.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Gives access to the link the file was posted to.
	 * 
	 * @return The link to where the file was posted.
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Gives access to the expire time of the uploaded file.
	 * 
	 * @return The number of days, since posting, that the file remains on file.io.
	 */
	public String getExpiry() {
		return expiry;
	}

	/**
	 * Gives access to the error code (if applicable).
	 * 
	 * @return The error code from the response.
	 */
	public String getError() {
		return error;
	}

	/**
	 * Gives access to the error message (if applicable).
	 * 
	 * @return The error message from the response.
	 */
	public String getMessage() {
		return message;
	}
}
