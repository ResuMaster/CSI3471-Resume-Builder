package to.us.resume_builder.business.server_connect.request;

public class FailedRequestException extends Exception {

	/** Make my IDE shut up */
	private static final long serialVersionUID = -5338684261220052917L;

	/** The response code which caused this Exception to be thrown. */
	private final int responseCode;

	/**
	 * Construct a FailedRequestException for a specific error code with a given
	 * message.
	 * 
	 * @param msg          The message to display to the user.
	 * @param responseCode The incorrect HTTP response code.
	 */
	public FailedRequestException(String msg, int responseCode) {
		super(msg);
		this.responseCode = responseCode;
	}

	/**
	 * Construct a FailedRequestException for a specific error code with a generic
	 * message.
	 * 
	 * @param errorCode The incorrect HTTP response code.
	 */
	public FailedRequestException(int errorCode) {
		super("The requested operation returned an incorrect error code.");
		this.responseCode = errorCode;
	}

	@Override
	public String getMessage() {
		StringBuilder bldr = new StringBuilder(super.getMessage());
		bldr.append(" (Response gotten: ").append(responseCode).append(")");
		return bldr.toString();
	}
}
