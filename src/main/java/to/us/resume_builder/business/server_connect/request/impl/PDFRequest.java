package to.us.resume_builder.business.server_connect.request.impl;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;

import to.us.resume_builder.business.server_connect.request.BasicRequest;
import to.us.resume_builder.business.server_connect.request.RequestType;

/**
 *
 * @author Micah Schiewe
 */
public class PDFRequest extends BasicRequest<InputStream> {

	/**
	 * TODO: fill in
	 */
	public PDFRequest() {
		super(RequestType.POST, "/pdf");
	}

	/**
	 * TODO: fill in
	 * @param arguments in a Name1, Val1, ..., NameN, ValN format.
	 * @return
	 */
	@Override
	protected URI doMakeURI(String... arguments) {
		return URI.create(SITE.concat(path));
	}

	/**
	 * TODO: fill in
	 * @return
	 */
	@Override
	protected BodyHandler<InputStream> doMakeResponseBuilder() {
		return HttpResponse.BodyHandlers.ofInputStream();
	}

	/**
	 * TODO: fill in
	 * @param arguments The arguments potentially needed for this request.
	 * @return
	 */
	@Override
	protected BodyPublisher doMakeBody(String... arguments) {
		return HttpRequest.BodyPublishers.ofString(getArguments(arguments));
	}
}
