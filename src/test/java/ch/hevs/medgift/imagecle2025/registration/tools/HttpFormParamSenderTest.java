package ch.hevs.medgift.imagecle2025.registration.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ch.hevs.medgift.imageclef2025.registration.config.Config;
import ch.hevs.medgift.imageclef2025.registration.tools.HttpFormParamSender;

public class HttpFormParamSenderTest {

	// Existing means that email exists in CLEF DB
	// Make sure that email exists in CLEF DB
	private static Map<String, String> existingBasicParticipantHttpParams = Map.of(Config.CLEF_EMAIL_STRING,
			"agentili@ucsd.edu", Config.CLEF_COUNTRY_STRING, "US", Config.CLEF_AFFILIATION_STRING,
			"San Diego VA HCS & UCSD", Config.CLEF_PERSON_STRING, "Amilcare Gentili", Config.CLEF_TEAM_STRING,
			"SDVA_UCSD");

	private static Map<String, String> allTasksHttpParams = new HashMap<>();

	@BeforeAll
	public static void setUp() {
		Config.TASK_AI4MEDIA_TO_HTTP_PARAM.values().forEach(param -> {
			if (!allTasksHttpParams.containsKey(param))
				allTasksHttpParams.put(param, param);
		});
	}

	@Test
	public void whenGetFormDataXwwwUrlEncodedCalled_returnCorrectString() {

		Map<String, String> expected = Arrays.asList("haha=huhu&hehe=hihi&hoho=h%C3%B6h%C3%B6".split("&")).stream()
				.map(p -> p.split("=")).collect(Collectors.toMap(p -> p[0], p -> p[1]));

		Map<String, String> actual = Arrays
				.asList(HttpFormParamSender
						.getFormDataXwwwUrlEncoded(Map.of("haha", "huhu", "hehe", "hihi", "hoho", "höhö")).split("&"))
				.stream().map(p -> p.split("=")).collect(Collectors.toMap(p -> p[0], p -> p[1]));

		assertEquals(expected, actual);
	}

	@Test
	public void whenDoPostForNewRegistrationWithExistingEmail_return400ErrorAndCorrectStringContent()
			throws IOException, InterruptedException {

		HttpFormParamSender sender = HttpFormParamSender.getInstance(Config.CLEF_API_USERNAME,
				Config.CLEF_API_PASSWORD);
		Map<String, String> params = new HashMap<>();
		params.putAll(existingBasicParticipantHttpParams);
		params.putAll(allTasksHttpParams);
		HttpResponse<String> response = sender.doPost(params, Config.CLEF_API_IMPORT_URL);

		int expected1 = 400;
		int actual1 = response.statusCode();

		String actual2 = response.body().toLowerCase();
		String expected2 = "DUPLICATED_REGISTRATION";

		assertEquals(expected1, actual1);
		assertTrue(actual2.contains(expected2.toLowerCase()));

	}

}
