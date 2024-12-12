package ch.hevs.medgift.imageclef2025.registration.send;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import ch.hevs.medgift.imageclef2025.registration.config.Config;
import ch.hevs.medgift.imageclef2025.registration.entities.AI4MediaRegistration;
import lombok.Getter;

public class ParticipantRegistrationHttpParams {

	private final static Logger LOGGER = Logger.getLogger(Config.MAIN_LOGGER_NAME);

	@Getter
	private Map<String, String> httpParamsRegisterNew = new HashMap<>();
	@Getter
	private Map<String, String> httpParamsUpdateExisting = new HashMap<>();
	@Getter
	private AI4MediaRegistration registration;

	private ParticipantRegistrationHttpParams(AI4MediaRegistration registration) {
		this.registration = registration;
		this.setHttpParams();
	}

	public static ParticipantRegistrationHttpParams getInstance(AI4MediaRegistration registration) {
		return new ParticipantRegistrationHttpParams(registration);
	}

	private void setHttpParams() {
		this.setParticipantHttpParamsRegisterNew(this.registration.contactEmail(), this.registration.person(),
				this.registration.teamName(), this.registration.countryCode(), this.registration.affiliation());
		this.setParticipantHttpParamsUpdateExisting(this.registration.contactEmail());
		this.setTaskHttpParams(this.registration.tasksRegistered());
	}

	private void setParticipantHttpParamsRegisterNew(String email, String contactPerson, String teamName,
			String countryCode, String affiliation) {
		this.httpParamsRegisterNew.put(Config.CLEF_EMAIL_STRING, email);
		this.httpParamsRegisterNew.put(Config.CLEF_PERSON_STRING, contactPerson);
		this.httpParamsRegisterNew.put(Config.CLEF_TEAM_STRING, teamName);
		this.httpParamsRegisterNew.put(Config.CLEF_COUNTRY_STRING, countryCode);
		this.httpParamsRegisterNew.put(Config.CLEF_AFFILIATION_STRING, affiliation);
	}

	private void setParticipantHttpParamsUpdateExisting(String email) {
		this.httpParamsUpdateExisting.put(Config.CLEF_EMAIL_STRING, email);
	}

	// If task name does not exist (taskParam == null), do not add it as param
	private void setTaskHttpParams(List<String> taskNamesAi4Media) {

		for (String taskName : taskNamesAi4Media) {

			String taskParam = Config.TASK_AI4MEDIA_TO_HTTP_PARAM.get(taskName);

			if (taskParam == null) {
				LOGGER.warning("task name '" + taskName + "' does not exist. Ignoring it.");
				continue;
			}

			if (!this.httpParamsRegisterNew.containsKey(taskName)) {
				this.httpParamsRegisterNew.put(taskParam, taskParam);

			}

			if (!this.httpParamsUpdateExisting.containsKey(taskName)) {
				this.httpParamsUpdateExisting.put(taskParam, taskParam);
			}

		}
	}

}
