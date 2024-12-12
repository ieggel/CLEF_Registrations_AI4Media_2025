package ch.hevs.medgift.imageclef2025.registration.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.opencsv.exceptions.CsvValidationException;

import ch.hevs.medgift.imageclef2025.registration.config.Config;
import ch.hevs.medgift.imageclef2025.registration.entities.AI4MediaRegistration;
import ch.hevs.medgift.imageclef2025.registration.read.AI4MediaRegistrations;
import ch.hevs.medgift.imageclef2025.registration.read.AI4MediaRegistrationsTest;

public class BasicRegistrationStatsTest {

	private static BasicRegistrationStats stats;
	private static List<AI4MediaRegistration> registrations;

	private final static List<String> CAPTION_EMAILS_EXPECTED = List.of("stanciu.cristi12@gmail.com",
			"stefan.liviu.daniel@gmail.com");
	private final static List<String> GANS_EMAILS_EXPECTED = List.of("stanciu.cristi12@gmail.com",
			"andrei.alexandra96@yahoo.com");
	private final static List<String> VQA_EMAILS_EXPECTED = List.of("stanciu.cristi12@gmail.com",
			"stefan.liviu.daniel@gmail.com");
	private final static List<String> MEDIQA_EMAILS_EXPECTED = List.of("stanciu.cristi12@gmail.com");
	private final static List<String> MULTIMODAL_REASON_EMAILS_EXPECTED = List.of("stanciu.cristi12@gmail.com",
			"stefan.liviu.daniel@gmail.com");
	private final static List<String> TOPICTO_EMAILS_EXPECTED = List.of("stanciu.cristi12@gmail.com",
			"stefan.liviu.daniel@gmail.com");

	private static List<String> captionEmailsActual;
	private static List<String> gansEmailsActual;
	private static List<String> vqaEmailsActual;
	private static List<String> mediqaEmailsActual;
	private static List<String> toPictoEmailsActual;
	private static List<String> multimodalReasonEmailsActual;

	@BeforeAll
	public static void setUp() throws CsvValidationException, FileNotFoundException, IOException, URISyntaxException,
			InterruptedException {

		AI4MediaRegistrations aiForMediaRegs = AI4MediaRegistrations
				.getInstance(AI4MediaRegistrationsTest.TEST_URL_REGISTRATIONS_AI4MEDIA);
		registrations = aiForMediaRegs.getRegistrations();
		registrations = registrations.subList(registrations.size() - 3, registrations.size());
		stats = BasicRegistrationStats.getInstance(registrations);

		captionEmailsActual = stats.getRegParticipantEmailsForTask(Config.CAPTION_TASK);
		gansEmailsActual = stats.getRegParticipantEmailsForTask(Config.GAN_TASK);
		vqaEmailsActual = stats.getRegParticipantEmailsForTask(Config.VQA_TASK);
		mediqaEmailsActual = stats.getRegParticipantEmailsForTask(Config.MEDIQA_TASK);
		toPictoEmailsActual = stats.getRegParticipantEmailsForTask(Config.TOPICTO_TASK);
		multimodalReasonEmailsActual = stats.getRegParticipantEmailsForTask(Config.MULTIOMODAL_REASON_TASK);

	}

	@Test
	public void whenGetRegsPerTasksCalled_returnCorrectNbrRegRerTask() {

		Map<String, Long> expected = Map.of(Config.CAPTION_TASK, 2L, Config.GAN_TASK, 2L, Config.VQA_TASK, 2L,
				Config.MEDIQA_TASK, 1L, Config.MULTIOMODAL_REASON_TASK, 2L, Config.TOPICTO_TASK, 2L);

		Map<String, Long> actual = stats.getNbrRegsPerTask();

		assertEquals(expected, actual);
	}

	@Test
	public void whenGetRegsPerOfficialTasksCalled_returnCorrectNbrRegRerTask() {

		Map<String, Long> expected = Map.of(Config.MEDICAL_TASK, 3L, Config.MULTIOMODAL_REASON_TASK, 2L,
				Config.TOPICTO_TASK, 2L);

		Map<String, Long> actual = stats.getNbrRegsPerOfficialTask();

		assertEquals(expected, actual);
	}

	@Test
	public void whenGetNbrParticipantsTotalCalled_returnCorrectNbrOfParticipantsInTotal() {

		int expected = 3;
		int actual = stats.getNbrParticipantsTotal();

		assertEquals(expected, actual);
	}

	@Test
	public void whenGetRegParticipantEmailsForTaskCalledForAllTasks_returnCorrectEmails() {

		assertTrue(CAPTION_EMAILS_EXPECTED.size() == captionEmailsActual.size()
				&& CAPTION_EMAILS_EXPECTED.containsAll(captionEmailsActual));
		assertTrue(GANS_EMAILS_EXPECTED.size() == gansEmailsActual.size()
				&& GANS_EMAILS_EXPECTED.containsAll(gansEmailsActual));
		assertTrue(VQA_EMAILS_EXPECTED.size() == vqaEmailsActual.size()
				&& VQA_EMAILS_EXPECTED.containsAll(vqaEmailsActual));
		assertTrue(MEDIQA_EMAILS_EXPECTED.size() == mediqaEmailsActual.size()
				&& MEDIQA_EMAILS_EXPECTED.containsAll(mediqaEmailsActual));
		assertTrue(MULTIMODAL_REASON_EMAILS_EXPECTED.size() == multimodalReasonEmailsActual.size()
				&& MULTIMODAL_REASON_EMAILS_EXPECTED.containsAll(multimodalReasonEmailsActual));
		assertTrue(TOPICTO_EMAILS_EXPECTED.size() == toPictoEmailsActual.size()
				&& TOPICTO_EMAILS_EXPECTED.containsAll(toPictoEmailsActual));

	}

	@Test
	public void whenGetRegParticipantEmailsForAllTasksCalled_returnCorrectEmails() {

		Map<String, List<String>> actualMap = stats.getRegParticipantEmailsForAllTasks();
		Config.TASK_AI4MEDIA_TO_HTTP_PARAM.keySet().forEach(task -> {
			List<String> expected = stats.getRegParticipantEmailsForTask(task);
			List<String> actual = actualMap.get(task);
			assertTrue(expected.size() == actual.size() && expected.containsAll(actual));
		});
	}

}
