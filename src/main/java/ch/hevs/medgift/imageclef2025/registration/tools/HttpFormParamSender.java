package ch.hevs.medgift.imageclef2025.registration.tools;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpFormParamSender {

	private HttpClient client;

	private HttpFormParamSender(String username, String password) {
		this.client = HttpClient.newBuilder().authenticator(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password.toCharArray());
			}
		}).build();
	}

	public static HttpFormParamSender getInstance(String username, String password) {
		return new HttpFormParamSender(username, password);
	}

	public HttpResponse<String> doPost(Map<String, String> formParams, String url)
			throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
				.header("Content-Type", "application/x-www-form-urlencoded")
				.POST(BodyPublishers.ofString(getFormDataXwwwUrlEncoded(formParams))).build();

		return client.send(request, HttpResponse.BodyHandlers.ofString());
	}

	public static String getFormDataXwwwUrlEncoded(Map<String, String> params) {
		return params.entrySet().stream()
				.map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
				.collect(Collectors.joining("&"));
	}

}
