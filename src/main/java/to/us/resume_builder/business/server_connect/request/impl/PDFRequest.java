package to.us.resume_builder.business.server_connect.request.impl;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;

import to.us.resume_builder.business.server_connect.request.BasicRequest;
import to.us.resume_builder.business.server_connect.request.RequestType;

public class PDFRequest extends BasicRequest<InputStream> {

	public PDFRequest() {
		super(RequestType.POST, "/pdf");
	}

	@Override
	protected URI doMakeURI(String... arguments) {
		return URI.create(SITE.concat(path));
	}

	@Override
	protected BodyHandler<InputStream> doMakeResponseBuilder() {
		return HttpResponse.BodyHandlers.ofInputStream();
	}

	@Override
	protected BodyPublisher doMakeBody(String... arguments) {
		return HttpRequest.BodyPublishers.ofString(getArguments(arguments));
	}
}
