package ch.hevs.medgift.imageclef2025.registration.read;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.opencsv.exceptions.CsvValidationException;

import ch.hevs.medgift.imageclef2025.registration.config.AI4MediaFields;
import ch.hevs.medgift.imageclef2025.registration.config.Config;
import ch.hevs.medgift.imageclef2025.registration.entities.AI4MediaRegistration;
import ch.hevs.medgift.imageclef2025.registration.tools.CountryCodeConverter;
import ch.hevs.medgift.imageclef2025.registration.tools.Utils;

public class AI4MediaRegistrations implements Registrations {

	private final static Logger LOGGER = Logger.getLogger(Config.MAIN_LOGGER_NAME);

	private InputStream source;

	private static CountryCodeConverter countryCodeConverter;

	static {
		try {
			countryCodeConverter = CountryCodeConverter.getInstance(Config.COUNTRIES_CSV_PATH);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private AI4MediaRegistrations(InputStream source)
			throws CsvValidationException, FileNotFoundException, IOException {
		this.source = source;
	}

	private AI4MediaRegistrations(String url) throws CsvValidationException, FileNotFoundException, IOException,
			InterruptedException, URISyntaxException {
		this(Utils.getInputStreamFromHttpGet(url));
	}

	public static AI4MediaRegistrations getInstance(InputStream source)
			throws CsvValidationException, FileNotFoundException, IOException {
		return new AI4MediaRegistrations(source);
	}

	public static AI4MediaRegistrations getInstance(String url) throws CsvValidationException, FileNotFoundException,
			IOException, InterruptedException, URISyntaxException {
		return new AI4MediaRegistrations(url);
	}

	@Override
	public List<AI4MediaRegistration> getRegistrations() {
		JSONObject jsonObject = AI4MediaRegistrations.createJsonObject(this.source);
		return AI4MediaRegistrations.createRegistrations(jsonObject);
	}

	private static JSONObject createJsonObject(InputStream source) {
		StringBuilder sb = new StringBuilder();
		try (Scanner scanner = new Scanner(source)) {
			while (scanner.hasNextLine()) {
				sb.append(scanner.nextLine());
			}
		}
		return new JSONObject(sb.toString());
	}

	private static List<AI4MediaRegistration> createRegistrations(JSONObject jsonObject) {

		List<AI4MediaRegistration> registrations = new ArrayList<>();

		jsonObject.getJSONArray(Config.AI4MEDIA_CONTENT_KEY).forEach(content -> {
			var answers = ((JSONObject) content).getJSONObject(Config.AI4MEDIA_VALUE_ARRAY_KEY);

			var teamName = answers.getJSONObject(AI4MediaFields.TEAM.getKey()).getString(Config.AI4MEDIA_VALUE_KEY)
					.trim();
			var person = answers.getJSONObject(AI4MediaFields.PERSON.getKey()).getString(Config.AI4MEDIA_VALUE_KEY)
					.trim();
			var affiliation = answers.getJSONObject(AI4MediaFields.AFFILIATION.getKey().trim())
					.getString(Config.AI4MEDIA_VALUE_KEY);
			var countryName = answers.getJSONObject(AI4MediaFields.COUNTRY.getKey().trim())
					.getString(Config.AI4MEDIA_VALUE_KEY);
			var countryCode = countryCodeConverter.getIso2(countryName).trim();
			var contactEmail = answers.getJSONObject(AI4MediaFields.EMAIL.getKey()).getString(Config.AI4MEDIA_VALUE_KEY)
					.trim();
			var tasksJsonArray = answers.getJSONObject(AI4MediaFields.TASKS.getKey())
					.getJSONArray(Config.AI4MEDIA_VALUE_KEY);
			List<String> tasksRegistered = new ArrayList<>(tasksJsonArray.length());
			tasksJsonArray.forEach(taskName -> {
				tasksRegistered.add(((String) taskName));
			});

			var registration = new AI4MediaRegistration(teamName, affiliation, countryCode, person, contactEmail,
					tasksRegistered);
			registrations.add(registration);

		});
		return registrations;
	}

}
