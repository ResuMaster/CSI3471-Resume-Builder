package to.us.resume_builder.business.server_connect.request;

import to.us.resume_builder.business.ApplicationConfiguration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.time.Duration;
import java.util.Arrays;

/**
 * Basic framework for sending an HTTP request. Handles a single request.
 * 
 * @author Micah
 */
public abstract class BasicRequest<T> {

	/** The client leveraged by all subclasses. */
	protected static final HttpClient CLIENT;

	/** The destination for all requests */
	protected static final String SITE;

	/** Initializes the client and address to query. */
	static {
		CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
		SITE = ApplicationConfiguration.getInstance().getString("request.url");
	}

	/** Charset to send requests using */
	protected static final String CHARSET = "UTF-8";
	/** Separator for elements in a query string */
	protected static final String SEPARATOR = "&";
	/** Assignment for parameters in a query string */
	protected static final String ASSIGN = "=";
	/** Separates the URL from the query string */
	protected static final String PARAM_SPLIT = "?";

	/** Assumed timeout, in seconds, of the method */
	protected static final int TIMEOUT = 60;

	/** The type of request this handles */
	private RequestType type;

	/** The path within the site */
	protected final String path;

	/**
	 * Create a request with a specified type
	 * 
	 * @param type The type of request this request handles
	 */
	public BasicRequest(RequestType type, String specificPath) {
		this.type = type;
		this.path = specificPath;
	}

	/**
	 * Creates and sends the specific request this class handles.
	 * <p>
	 * Example of Design Pattern: Template Method
	 * 
	 * @param arguments
	 * @return The http response from the request this class handles
	 * @throws InterruptedException If the operation is interrupted
	 * @throws IOException          If an I/O exception occurs in sending or
	 *                              receiving.
	 */
	public final HttpResponse<T> sendRequest(String... arguments) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(doMakeURI(arguments)).setHeader("Content-Type", doGetType())
                .method(type.name(), doMakeBody(arguments)).timeout(Duration.ofSeconds(TIMEOUT)).build();
        return CLIENT.send(request, doMakeResponseBuilder());
    }

    /**
     * 
     * Creates the query string. Acts as utility for subclasses.
     * 
     * @param arguments The arguments, in a [Name1, Val1, ..., NameN, ValN] format.
     * @return The query string encapsulating the arguments list
     */
    protected final String getArguments(String... arguments) {
        StringBuilder sb = new StringBuilder("");

        // Assume my user didn't read the manual, protect from making a bad request.
        assert arguments.length
                % 2 == 0 : "Invalid number of parameters to getPublisher! Expected an even number, but got: "
                        + Arrays.toString(arguments);

        for (int i = 0; i < arguments.length; i += 2) {
            // varName=varVal
            try {
                sb.append(URLEncoder.encode(arguments[i], CHARSET));
                sb.append(ASSIGN);
                sb.append(URLEncoder.encode(arguments[i + 1], CHARSET));
            } catch (UnsupportedEncodingException e) {
                // Should not happen, UTF-8 is standard
                e.printStackTrace();
            }

            // If there are more, add the variable separator
            if (i + 1 < arguments.length)
                sb.append(SEPARATOR);
        }

        return sb.toString();
    }

    /**
     * Gives access to the type of request being sent. Unless non-character data, or
     * very large data files, are being sent, there is no reason to override this
     * method in subclasses.
     * 
     * @return The type of the message
     */
    protected String doGetType() {
        return "application/x-www-form-urlencoded";
    }

    /**
     * Gets the URI for the request.
     * 
     * @param arguments in a [Name1, Val1, ..., NameN, ValN] format.
     * @return The URI for this request.
     */
    protected abstract URI doMakeURI(String... arguments);

    /**
     * Gets the http body for this request.
     * 
     * @param arguments The arguments potentially needed for this request.
     * @return The body for this request.
     */
    protected abstract HttpRequest.BodyPublisher doMakeBody(String... arguments);

    /**
     * Creates the response builder for the response.
     * 
     * @return The handler to adapt the http response to a Java object.
     */
    protected abstract BodyHandler<T> doMakeResponseBuilder();
}
