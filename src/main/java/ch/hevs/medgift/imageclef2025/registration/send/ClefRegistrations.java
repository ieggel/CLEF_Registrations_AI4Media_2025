package ch.hevs.medgift.imageclef2025.registration.send;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import ch.hevs.medgift.imageclef2025.registration.config.Config;
import ch.hevs.medgift.imageclef2025.registration.entities.AI4MediaRegistration;
import ch.hevs.medgift.imageclef2025.registration.tools.HttpFormParamSender;

public class ClefRegistrations {

	private final static Logger LOGGER = Logger.getLogger(Config.MAIN_LOGGER_NAME);

	private HttpFormParamSender sender = HttpFormParamSender.getInstance(Config.CLEF_API_USERNAME,
			Config.CLEF_API_PASSWORD);

	private List<ParticipantRegistrationHttpParams> participantRegsHttpParams;

	private ClefRegistrations(List<AI4MediaRegistration> registrations) {
		this.setParticipantRegHttpParams(registrations);
	}

	private void setParticipantRegHttpParams(List<AI4MediaRegistration> registrations) {
		this.participantRegsHttpParams = registrations.stream()
				.map(reg -> ParticipantRegistrationHttpParams.getInstance(reg)).collect(Collectors.toList());
	}

	public static ClefRegistrations getInstance(List<AI4MediaRegistration> registrations) {
		return new ClefRegistrations(registrations);
	}

	public void registerAndUpdate() throws IOException, InterruptedException, ClefRegistrationSendException {
		List<ParticipantRegistrationHttpParams> existingRegs = this.registerNewTeams();
		this.updateExistingTeamsTasks(existingRegs);
	}

	// Returns Http parms for participants that are already registered in the CLEF
	// DB
	public List<ParticipantRegistrationHttpParams> registerNewTeams()
			throws IOException, InterruptedException, ClefRegistrationSendException {

		List<ParticipantRegistrationHttpParams> participantRegsHttpParamsForUpdate = new ArrayList<>();

		for (ParticipantRegistrationHttpParams reg : this.participantRegsHttpParams) {
			Map<String, String> partParamsRegNew = reg.getHttpParamsRegisterNew();

			HttpResponse<String> response = sender.doPost(partParamsRegNew, Config.CLEF_API_IMPORT_URL);

			AI4MediaRegistration regDetail = reg.getRegistration();

			if (response.statusCode() == 200) {
				LOGGER.info("++++++++++++++++++++++++++++");
				LOGGER.info(
						regDetail.contactEmail() + " registered successfully for tasks " + regDetail.tasksRegistered());
				LOGGER.fine("All params: " + partParamsRegNew);
				LOGGER.finest("Response body: " + response.body());
			} else if (participantExists(response)) {
				LOGGER.info("----------------------------");
				LOGGER.info(regDetail.contactEmail() + " already exists in CLEF DB. Marked for update.");
				participantRegsHttpParamsForUpdate.add(reg);
			} else {
				LOGGER.severe("############################");
				LOGGER.severe("Invalid CLEF New registration request for the following params: " + partParamsRegNew);
				throwClefRegistrationSendException(response);
			}

		}

		return participantRegsHttpParamsForUpdate;
	}

	public void updateExistingTeamsTasks(List<ParticipantRegistrationHttpParams> participantRegsHttpParamsForUpdate)
			throws IOException, InterruptedException, ClefRegistrationSendException {

		for (ParticipantRegistrationHttpParams reg : participantRegsHttpParamsForUpdate) {
			Map<String, String> partParamsRegUpdate = reg.getHttpParamsUpdateExisting();
			HttpResponse<String> response = sender.doPost(partParamsRegUpdate, Config.CLEF_API_UPDATE_URL);

			AI4MediaRegistration regUpdateDetail = reg.getRegistration();

			if (response.statusCode() == 200) {
				LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				LOGGER.info(regUpdateDetail.contactEmail() + " updated successfully to tasks "
						+ regUpdateDetail.tasksRegistered());
				LOGGER.fine("All params: " + partParamsRegUpdate);
				LOGGER.finest("Response body: " + response.body());
			} else {
				LOGGER.severe("############################");
				LOGGER.severe("Invalid CLEF New registration request for the following params: " + partParamsRegUpdate);
				throwClefRegistrationSendException(response);
			}
		}
	}

	private static void throwClefRegistrationSendException(HttpResponse<String> response)
			throws ClefRegistrationSendException {
		throw new ClefRegistrationSendException("Invalid CLEF request. HTTP status code: " + response.statusCode()
				+ " Response body: " + response.body());

	}

	private static boolean participantExists(HttpResponse<String> response) {
		return (response.statusCode() == 400)
				&& response.body().toString().toUpperCase().contains("DUPLICATED_REGISTRATION");
	}

}
