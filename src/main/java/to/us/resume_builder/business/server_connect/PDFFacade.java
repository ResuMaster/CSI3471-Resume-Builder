package to.us.resume_builder.business.server_connect;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import to.us.resume_builder.business.server_connect.fileio_response.FileIOResponse;
import to.us.resume_builder.business.server_connect.fileio_response.FileIOResponseParser;
import to.us.resume_builder.business.server_connect.request.FailedRequestException;
import to.us.resume_builder.business.server_connect.request.impl.PDFRequest;

public class PDFFacade {

	/** The status code indicating the PDF compile request succeeded */
	private static final int DOWNLOAD_SUCCESS = 200;

	/** The status code indicating the PDF upload request succeeded */
	private static final int UPLOAD_SUCCESS = 200;

	private static PDFFacade singleton = null;
	private static final Object LOCK = new Object();

	private PDFRequest req;

	/**
	 * Compiles the given LaTeX string into a PDF.
	 * 
	 * @param latex The LaTeX string to compile.
	 * @return The bytes of the resultant PDF.
	 * @throws InterruptedException   If the operation is interrupted
	 * @throws IOException            If an I/O exception occurs in sending or
	 *                                receiving.
	 * @throws FailedRequestException If the response indicates that an error
	 *                                occurred server-side.
	 */
	public byte[] getPDF(String latex) throws IOException, InterruptedException, FailedRequestException {
		// Get and vet response
		HttpResponse<InputStream> resp = req.sendRequest("latex", latex, "url", "false");
		catchError(resp, DOWNLOAD_SUCCESS);

		// Return file
		return resp.body().readAllBytes();
	}

	/**
	 * Compiles the given LaTeX string into a PDF, which is uploaded to file.io.
	 * 
	 * @param latex The LaTeX string to compile.
	 * @return file.io's response, or null if the response was corrupted.
	 * @throws InterruptedException   If the operation is interrupted
	 * @throws IOException            If an I/O exception occurs in sending or
	 *                                receiving.
	 * @throws FailedRequestException If the response indicates that an error
	 *                                occurred server-side.
	 */
	public FileIOResponse uploadPDF(String latex) throws IOException, InterruptedException, FailedRequestException {
		// Get and vet response
		HttpResponse<InputStream> resp = req.sendRequest("latex", latex, "url", "true");
		catchError(resp, UPLOAD_SUCCESS);

		// Send back response. Split up into multiple lines for readability.
		byte[] result = resp.body().readAllBytes();
		String txtResp = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(result)).toString();
		return FileIOResponseParser.getManager().parseResponse(txtResp);
	}

	/**
	 * Checks the given response for a valid code, and throws a correct
	 * FailedRequestException if it does not measure up.
	 * 
	 * @param <T>         The type of the response. Unused in the method, but
	 *                    present for general safety.
	 * @param resp        The response to analyze for correctness
	 * @param successCode The expected response code
	 * @throws FailedRequestException An exception with a properly-prepared message
	 *                                indicating what happened with the request.
	 */
	private <T> void catchError(HttpResponse<T> resp, int successCode) throws FailedRequestException {
		if (resp.statusCode() != successCode) {
			String error = resp.headers().firstValue("error").orElse(null);
			if (error != null)
				throw new FailedRequestException(error, resp.statusCode());
			else
				throw new FailedRequestException(resp.statusCode());
		}
	}

	/**
	 * Offer access to the PDFFacade singleton.
	 * 
	 * @return The handler for calls to the PDF Spring service.
	 */
	public static PDFFacade getPDFHandle() {
		if (singleton == null) {
			synchronized (LOCK) {
				if (singleton == null)
					singleton = new PDFFacade();
			}
		}
		return singleton;
	}

	private PDFFacade() {
		req = new PDFRequest();
	}
}
