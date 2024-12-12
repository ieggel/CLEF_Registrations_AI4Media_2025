package ch.hevs.medgift.imageclef2025.registration.read;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.opencsv.exceptions.CsvValidationException;

import ch.hevs.medgift.imageclef2025.registration.config.AI4MediaFields;
import ch.hevs.medgift.imageclef2025.registration.config.Config;
import ch.hevs.medgift.imageclef2025.registration.entities.AI4MediaRegistration;
import ch.hevs.medgift.imageclef2025.registration.tools.CountryCodeConverter;

public class AI4MediaRegistrationsTest {

	public final static String TEST_URL_REGISTRATIONS_AI4MEDIA = "https://support.aimultimedialab.ro/imageclef/getImageCLEF2024Registrations.php?apiKey=3154948189c077bc9c7f1fa1722ed2ae&formId=243262631549357&filter=%7B%22workflowStatus%22:%22APPROVED%22%7D&api=true";

	@Test
	public void shouldCreateCorrectRegistrationFromMockFile()
			throws CsvValidationException, FileNotFoundException, IOException {

		InputStream fileSource = AI4MediaRegistrationsTest.class.getClassLoader().getResourceAsStream("test_regs.json");

		CountryCodeConverter ccConverter = CountryCodeConverter.getInstance(Config.COUNTRIES_CSV_PATH);

		AI4MediaRegistrations ai4mediaRegs = AI4MediaRegistrations.getInstance(fileSource);
		List<AI4MediaRegistration> results = ai4mediaRegs.getRegistrations();

		List<String> regExpectedTasks1 = new ArrayList<>(List.of("ImageCLEFmed Caption", "ImageCLEFmed MEDVQA",
				"ImageCLEFmed MEDIQA-MAGIC", "ImageCLEFmed GANs", "ImageCLEFtoPicto", "MultimodalReasoning"));
		AI4MediaRegistration regExpected1 = new AI4MediaRegistration("AIMultimediaLab",
				"National University of Science and Technology Politehnica Bucharest", ccConverter.getIso2("Romania"),
				"Cristian STANCIU", "stanciu.cristi12@gmail.com", regExpectedTasks1);

		List<String> regExpectedTasks2 = new ArrayList<>(List.of("ImageCLEFmed GANs"));
		AI4MediaRegistration regExpected2 = new AI4MediaRegistration("AIMultimediaLab",
				"National University of Science and Technology Politehnica Bucharest", ccConverter.getIso2("Romania"),
				"Alexandra ANDREI", "andrei.alexandra96@yahoo.com", regExpectedTasks2);

		List<String> regExpectedTasks3 = new ArrayList<>(
				List.of("ImageCLEFmed Caption", "ImageCLEFmed MEDVQA", "ImageCLEFtoPicto", "MultimodalReasoning"));
		AI4MediaRegistration regExpected3 = new AI4MediaRegistration("AIMultimediaLab",
				"National University of Science and Technology Politehnica Bucharest", ccConverter.getIso2("Romania"),
				"Liviu \u0218TEFAN", "stefan.liviu.daniel@gmail.com", regExpectedTasks3);

		assertEquals(results.get(0), regExpected1);
		assertEquals(results.get(1), regExpected2);
		assertEquals(results.get(2), regExpected3);

	}

	@Test
	public void shouldCreateRegistrationFromURL() throws CsvValidationException, FileNotFoundException, IOException,
			InterruptedException, URISyntaxException {

		CountryCodeConverter ccConverter = CountryCodeConverter.getInstance(Config.COUNTRIES_CSV_PATH);

		AI4MediaRegistrations ai4mediaRegs = AI4MediaRegistrations.getInstance(TEST_URL_REGISTRATIONS_AI4MEDIA);
		var allResults = ai4mediaRegs.getRegistrations();
		var last3Results = allResults.subList(allResults.size() - 3, allResults.size());

		List<String> regExpectedTasks1 = new ArrayList<>(List.of("ImageCLEFmed Caption", "ImageCLEFmed MEDVQA",
				"ImageCLEFmed MEDIQA-MAGIC", "ImageCLEFmed GANs", "ImageCLEFtoPicto", "MultimodalReasoning"));
		AI4MediaRegistration regExpected1 = new AI4MediaRegistration("AIMultimediaLab",
				"National University of Science and Technology Politehnica Bucharest", ccConverter.getIso2("Romania"),
				"Cristian STANCIU", "stanciu.cristi12@gmail.com", regExpectedTasks1);

		List<String> regExpectedTasks2 = new ArrayList<>(List.of("ImageCLEFmed GANs"));
		AI4MediaRegistration regExpected2 = new AI4MediaRegistration("AIMultimediaLab",
				"National University of Science and Technology Politehnica Bucharest", ccConverter.getIso2("Romania"),
				"Alexandra ANDREI", "andrei.alexandra96@yahoo.com", regExpectedTasks2);

		List<String> regExpectedTasks3 = new ArrayList<>(
				List.of("ImageCLEFmed Caption", "ImageCLEFmed MEDVQA", "ImageCLEFtoPicto", "MultimodalReasoning"));
		AI4MediaRegistration regExpected3 = new AI4MediaRegistration("AIMultimediaLab",
				"National University of Science and Technology Politehnica Bucharest", ccConverter.getIso2("Romania"),
				"Liviu \u0218TEFAN", "stefan.liviu.daniel@gmail.com", regExpectedTasks3);

		System.out.println(last3Results.get(2));

		assertEquals(last3Results.get(0), regExpected1);
		assertEquals(last3Results.get(1), regExpected2);
		assertEquals(last3Results.get(2), regExpected3);

	}

	// Check out values from JSON file
	public static void main(String[] args) throws CsvValidationException, FileNotFoundException, IOException,
			InterruptedException, URISyntaxException {
		// checkJsonFileValues();
		checkAI4MediaApiValues();
	}

	public static void checkAI4MediaApiValues() throws CsvValidationException, FileNotFoundException, IOException,
			InterruptedException, URISyntaxException {
		AI4MediaRegistrations regs = AI4MediaRegistrations.getInstance(Config.URL_AI4MEDIA_REGS);
		regs.getRegistrations().forEach(reg -> {
			System.out.println(reg);
		});
	}

	public static void checkJsonFileValues() {

		final InputStream is = AI4MediaRegistrationsTest.class.getClassLoader().getResourceAsStream("test_regs.json");

		StringBuilder sb = new StringBuilder();

		try (Scanner scanner = new Scanner(is)) {
			while (scanner.hasNextLine()) {
				sb.append(scanner.nextLine());
			}

		}
		JSONObject jsonObject = new JSONObject(sb.toString());

		jsonObject.getJSONArray(Config.AI4MEDIA_CONTENT_KEY).forEach(content -> {
			var answers = ((JSONObject) content).getJSONObject(Config.AI4MEDIA_VALUE_ARRAY_KEY);

			var team = answers.getJSONObject(AI4MediaFields.TEAM.getKey()).getString(Config.AI4MEDIA_VALUE_KEY);
			var person = answers.getJSONObject(AI4MediaFields.PERSON.getKey()).getString(Config.AI4MEDIA_VALUE_KEY);
			var affiliation = answers.getJSONObject(AI4MediaFields.AFFILIATION.getKey())
					.getString(Config.AI4MEDIA_VALUE_KEY);
			var country = answers.getJSONObject(AI4MediaFields.COUNTRY.getKey()).getString(Config.AI4MEDIA_VALUE_KEY);
			var email = answers.getJSONObject(AI4MediaFields.EMAIL.getKey()).getString(Config.AI4MEDIA_VALUE_KEY);
			var tasksJsonArray = answers.getJSONObject(AI4MediaFields.TASKS.getKey())
					.getJSONArray(Config.AI4MEDIA_VALUE_KEY);
			List<String> taskNames = new ArrayList<>(tasksJsonArray.length());
			tasksJsonArray.forEach(taskName -> {
				taskNames.add(((String) taskName));
			});
			System.out.printf("%s, %s, %s, %s, %s\n", team, affiliation, country, person, email);
			System.out.println(taskNames);
		});
	}

}
