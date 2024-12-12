package ch.hevs.medgift.imagecle2025.registration.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import ch.hevs.medgift.imageclef2025.registration.tools.Utils;

public class UtilsTest {

	public final static String RFC5280_TEST_FILENAME = "www.york.ac.uk-httpsource.txt";
	public final static String TEST_URL = "https://www.york.ac.uk/teaching/cws/wws/webpage1.html";

	@Test
	public void shouldReturnCorrectInputStreamFromHttpGet()
			throws IOException, InterruptedException, URISyntaxException {

		var inputStreamFile = UtilsTest.class.getClassLoader().getResourceAsStream(UtilsTest.RFC5280_TEST_FILENAME);
		var inputStreamHttp = Utils.getInputStreamFromHttpGet(TEST_URL);

		var expected = "";
		var actual = "";

		// \\A is the regex for the beginning of the input
		// This effectively causes the Scanner to read the entire input as one single token.
		try (Scanner sc = new Scanner(inputStreamFile)) {
			expected = sc.useDelimiter("\\A").next();
		}

		try (Scanner sc = new Scanner(inputStreamHttp)) {
			actual = sc.useDelimiter("\\A").next();
		}

		assertEquals(expected, actual);

	}

}
