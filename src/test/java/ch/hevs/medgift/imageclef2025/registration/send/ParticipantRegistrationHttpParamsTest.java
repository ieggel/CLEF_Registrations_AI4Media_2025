package ch.hevs.medgift.imageclef2025.registration.send;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ch.hevs.medgift.imageclef2025.registration.config.Config;
import ch.hevs.medgift.imageclef2025.registration.entities.AI4MediaRegistration;

public class ParticipantRegistrationHttpParamsTest {

	private static String TEAM = "team1";
	private static String AFFILIATION = "affiliation1";
	private static String COUNTRY_CODE = "countryCode1";
	private static String NAME = "first1 last1";
	private static String EMAIL = "email1";

	static AI4MediaRegistration regAllTasks;
	static AI4MediaRegistration reg3RandomTaks;

	static Map<String, String> expectedBasicParticipantHttpParamsForNew;
	static Map<String, String> expectedBasicParticipantHttpParamsForUpdate;

	static Map<String, String> expectedAllTasksHttpParams;
	static Map<String, String> expected3RandomTasksHttpParams;

	@BeforeAll
	public static void setUp() {
		List<String> allTaskNamesAi4Media = new ArrayList<String>(Config.TASK_AI4MEDIA_TO_HTTP_PARAM.keySet());
		regAllTasks = new AI4MediaRegistration(TEAM, AFFILIATION, COUNTRY_CODE, NAME, EMAIL, allTaskNamesAi4Media);

		List<String> taskNames = new ArrayList<>(Config.TASK_AI4MEDIA_TO_HTTP_PARAM.keySet());
		List<String> threeRandomExistingTaskNames = new ArrayList<>(3);
		for (int i = 1; i <= 3; i++) {
			int randomTaskNamesIndex = (int) (Math.random() * taskNames.size());
			threeRandomExistingTaskNames.add(taskNames.remove(randomTaskNamesIndex));
		}
		reg3RandomTaks = new AI4MediaRegistration(TEAM, AFFILIATION, COUNTRY_CODE, NAME, EMAIL,
				threeRandomExistingTaskNames);

		expectedBasicParticipantHttpParamsForNew = new HashMap<>();
		expectedBasicParticipantHttpParamsForNew.put(Config.CLEF_EMAIL_STRING, regAllTasks.contactEmail());
		expectedBasicParticipantHttpParamsForNew.put(Config.CLEF_COUNTRY_STRING, regAllTasks.countryCode());
		expectedBasicParticipantHttpParamsForNew.put(Config.CLEF_AFFILIATION_STRING, regAllTasks.affiliation());
		expectedBasicParticipantHttpParamsForNew.put(Config.CLEF_PERSON_STRING, regAllTasks.person());
		expectedBasicParticipantHttpParamsForNew.put(Config.CLEF_TEAM_STRING, regAllTasks.teamName());

		expectedBasicParticipantHttpParamsForUpdate = Map.of(Config.CLEF_EMAIL_STRING, EMAIL);

		expectedAllTasksHttpParams = Config.TASK_AI4MEDIA_TO_HTTP_PARAM.values().stream().distinct()
				.collect(Collectors.toMap(param -> param, param -> param));

		expected3RandomTasksHttpParams = new HashMap<>();
		threeRandomExistingTaskNames.forEach(taskName -> {
			if (!expected3RandomTasksHttpParams.containsKey(taskName))
				expected3RandomTasksHttpParams.put(Config.TASK_AI4MEDIA_TO_HTTP_PARAM.get(taskName),
						Config.TASK_AI4MEDIA_TO_HTTP_PARAM.get(taskName));
		});
	}

	@Test
	public void whenRegisterNewWithAlltasks_thenReturnCorrectParams() {

		ParticipantRegistrationHttpParams registrationHttpParams = ParticipantRegistrationHttpParams
				.getInstance(regAllTasks);
		Map<String, String> actual = registrationHttpParams.getHttpParamsRegisterNew();

		Map<String, String> expected = new HashMap<>();
		expected.putAll(expectedBasicParticipantHttpParamsForNew);
		expected.putAll(expectedAllTasksHttpParams);

		assertEquals(expected, actual);
	}

	@Test
	public void whenRegisterNewWith3RandomTasks_thenReturnCorrectParams() {

		ParticipantRegistrationHttpParams registrationHttpParams = ParticipantRegistrationHttpParams
				.getInstance(reg3RandomTaks);
		Map<String, String> actual = registrationHttpParams.getHttpParamsRegisterNew();

		Map<String, String> expected = new HashMap<>();
		expected.putAll(expectedBasicParticipantHttpParamsForNew);
		expected.putAll(expected3RandomTasksHttpParams);

		assertEquals(expected, actual);
	}

	@Test
	public void whenUpdateExistingWithAlltasks_thenReturnCorrectParams() {

		ParticipantRegistrationHttpParams registrationHttpParams = ParticipantRegistrationHttpParams
				.getInstance(regAllTasks);
		Map<String, String> actual = registrationHttpParams.getHttpParamsUpdateExisting();

		Map<String, String> expected = new HashMap<>();
		expected.putAll(expectedBasicParticipantHttpParamsForUpdate);
		expected.putAll(expectedAllTasksHttpParams);

		assertEquals(expected, actual);
	}

	@Test
	public void whenUpdateExistingWith3RandomTasks_thenReturnCorrectParams() {

		ParticipantRegistrationHttpParams registrationHttpParams = ParticipantRegistrationHttpParams
				.getInstance(reg3RandomTaks);
		Map<String, String> actual = registrationHttpParams.getHttpParamsUpdateExisting();

		Map<String, String> expected = new HashMap<>();
		expected.putAll(expectedBasicParticipantHttpParamsForUpdate);
		expected.putAll(expected3RandomTasksHttpParams);

		assertEquals(expected, actual);
	}

	@Test
	public void whenRegisterNewWithWrongTasks_thenDoNotAddThemAsParams() {

		AI4MediaRegistration reg = new AI4MediaRegistration(TEAM, AFFILIATION, COUNTRY_CODE, NAME, EMAIL,
				List.of("dd", "huhu", "hehe"));

		ParticipantRegistrationHttpParams registrationHttpParams = ParticipantRegistrationHttpParams.getInstance(reg);

		Map<String, String> expected = new HashMap<>();
		expected.putAll(expectedBasicParticipantHttpParamsForNew);

		Map<String, String> actual = registrationHttpParams.getHttpParamsRegisterNew();

		assertEquals(expected, actual);

	}

}
