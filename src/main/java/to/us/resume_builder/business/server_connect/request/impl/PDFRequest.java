package to.us.resume_builder.business.server_connect.request.impl;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;

import to.us.resume_builder.business.ApplicationConfiguration;
import to.us.resume_builder.business.server_connect.request.BasicRequest;
import to.us.resume_builder.business.server_connect.request.RequestType;

/**
 * Request for PDF compilation to the Spring server; must be loaded with the
 * LaTeX string to compile (as argument "latex"), and a boolean indicating
 * whether the resultant PDF should be sent back or uploaded.
 *
 * @author Micah Schiewe
 */
public class PDFRequest extends BasicRequest<InputStream> {

    /**
     * Constructs a PDFRequest, set as a POST request to the url in
     * ApplicationConfiguration/pdf
     */
    public PDFRequest() {
        super(RequestType.POST, ApplicationConfiguration.getInstance().getString("request.url").concat("/pdf"));
    }

    @Override
    protected URI doMakeURI(String... arguments) {
        return URI.create(path);
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
