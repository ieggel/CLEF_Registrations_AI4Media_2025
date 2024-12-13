package ch.hevs.medgift.imageclef2025.registration.config;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Config {

	public final static String MAIN_LOGGER_NAME = "ch.hevs.medgift.imagecled2024.registration";

	// public final static String URL_AI4MEDIA_REGS =
	// "https://support.aimultimedialab.ro/imageclef/getImageCLEF2024Registrations.php?apiKey=3154948189c077bc9c7f1fa1722ed2ae&formId=233444340721347&api=true&filter=%7B%22workflowStatus%22:%22APPROVED%22%7D";
	public final static String URL_AI4MEDIA_REGS = "https://support.aimultimedialab.ro/imageclef/getImageCLEF2024Registrations.php?apiKey=3154948189c077bc9c7f1fa1722ed2ae&formId=243262631549357&api=true&filter=%7B%22workflowStatus%22:%22APPROVED%22%7D";
	public final static String AI4MEDIA_CONTENT_KEY = "content";
	public final static String AI4MEDIA_VALUE_ARRAY_KEY = "answers";
	public final static String AI4MEDIA_VALUE_KEY = "answer";

	public final static String CAPTION_TASK = "ImageCLEFmed Caption";
	public final static String GAN_TASK = "ImageCLEFmed GANs";
	public final static String VQA_TASK = "ImageCLEFmed MEDVQA";
	public final static String MEDIQA_TASK = "ImageCLEFmed MEDIQA-MAGIC";
	// Argument task uses their own registration/submission
	// public final static String TOUCHE_ARGUMENT_TASK = "Touché-Argument-Images";
	public final static String TOPICTO_TASK = "ImageCLEFtoPicto";
	public final static String MULTIOMODAL_REASON_TASK = "MultimodalReasoning";

	public final static String MEDICAL_TASK = "medical";

	// Advisible to test these params via http requests before doing anything else
	// Registrations can be visualized via CLEF_EXPORT_REGS_URL
	public final static Map<String, String> TASK_AI4MEDIA_TO_HTTP_PARAM = Map.ofEntries(
			// ImageCLEFmed Caption
			new AbstractMap.SimpleEntry<String, String>(CAPTION_TASK, "imageclef_task1"),
			// ImageCLEFmed GANs
			new AbstractMap.SimpleEntry<String, String>(GAN_TASK, "imageclef_task1"),
			// ImageCLEFmed VQA
			new AbstractMap.SimpleEntry<String, String>(VQA_TASK, "imageclef_task1"),
			// ImageCLEFmed Mediqa-Magic
			new AbstractMap.SimpleEntry<String, String>(MEDIQA_TASK, "imageclef_task1"),
			// ToPicto
			new AbstractMap.SimpleEntry<String, String>(TOPICTO_TASK, "imageclef_task3"),
			// MultimodalReasoning
			new AbstractMap.SimpleEntry<String, String>(MULTIOMODAL_REASON_TASK, "imageclef_task4"));

	// Used for stats.
	public final static Map<String, String> TASK_AI4MEDIA_TO_TASK_OFFICIAL = Map.ofEntries(
			// ImageCLEFmed Caption
			new AbstractMap.SimpleEntry<String, String>(CAPTION_TASK, MEDICAL_TASK),
			// ImageCLEFmed GANs
			new AbstractMap.SimpleEntry<String, String>(GAN_TASK, MEDICAL_TASK),
			// ImageCLEFmed VQA
			new AbstractMap.SimpleEntry<String, String>(VQA_TASK, MEDICAL_TASK),
			// ImageCLEFmed Mediqa-Magic
			new AbstractMap.SimpleEntry<String, String>(MEDIQA_TASK, MEDICAL_TASK),
			// ToPicto
			new AbstractMap.SimpleEntry<String, String>(TOPICTO_TASK, TOPICTO_TASK),
			// MultimodalReasoning
			new AbstractMap.SimpleEntry<String, String>(MULTIOMODAL_REASON_TASK, MULTIOMODAL_REASON_TASK));

	public final static String COUNTRIES_CSV_PATH = "countries.csv";

	public final static String CLEF_TEAM_STRING = "team";

	public final static String CLEF_PERSON_STRING = "person";

	public final static String CLEF_COUNTRY_STRING = "country";

	public final static String CLEF_EMAIL_STRING = "email";

	public final static String CLEF_AFFILIATION_STRING = "affiliation";

	public final static String CLEF_API_IMPORT_URL = "https://clef2025-labs-registration.dei.unipd.it/private/importTeam.php";

	public final static String CLEF_API_UPDATE_URL = "https://clef2025-labs-registration.dei.unipd.it/private/updateImageCLEFTeam.php";

	public final static String CLEF_API_USERNAME = "clef2025";
	public final static String CLEF_API_PASSWORD = "2025_registrations";

	// Meant to be used manually to check CLEF registrations
	public final static String CLEF_EXPORT_REGS_URL = "https://clef2025-labs-registration.dei.unipd.it/private/exportRegistrations.php";

	public static List<String> mapTasksToOfficialTasks(List<String> tasks) {
		return tasks.stream().map(task -> Config.TASK_AI4MEDIA_TO_TASK_OFFICIAL.get(task)).distinct()
				.collect(Collectors.toList());
	}

}
