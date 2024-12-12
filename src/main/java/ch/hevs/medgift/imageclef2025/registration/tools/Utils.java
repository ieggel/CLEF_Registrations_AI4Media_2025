package ch.hevs.medgift.imageclef2025.registration.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Utils {

	public static InputStream getInputStreamFromHttpGet(String url)
			throws IOException, InterruptedException, URISyntaxException {

		final var request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
		final var client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS)
				.proxy(ProxySelector.getDefault()).build();

		final HttpResponse<InputStream> response = client.send(request, BodyHandlers.ofInputStream());
		return response.body();

	}

}
