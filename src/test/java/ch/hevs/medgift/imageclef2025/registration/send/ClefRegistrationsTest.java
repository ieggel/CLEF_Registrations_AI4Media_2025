package ch.hevs.medgift.imageclef2025.registration.send;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.opencsv.exceptions.CsvValidationException;

import ch.hevs.medgift.imageclef2025.registration.entities.AI4MediaRegistration;
import ch.hevs.medgift.imageclef2025.registration.read.AI4MediaRegistrations;
import ch.hevs.medgift.imageclef2025.registration.read.AI4MediaRegistrationsTest;

/*
 * INTEGRATION TEST
 * ****************
 * THIS TEST IS RUN MANUALLY, SO NO JUNIT IS USED
 * NEVER RUN IT AUTOMATICALLY, BECAUSE RECORDS
 * ARE ADDED TO REAL CLEF DB
 * 
 * This test is actually performed in prod (we had no other options)
 * Ask Nicola Ferro to remove the test entries afterwards
 */

public class ClefRegistrationsTest {

	public static void test() throws CsvValidationException, IOException, URISyntaxException, InterruptedException,
			ClefRegistrationSendException {

		AI4MediaRegistrations ai4MediaRegs = AI4MediaRegistrations
				.getInstance(AI4MediaRegistrationsTest.TEST_URL_REGISTRATIONS_AI4MEDIA);
		List<AI4MediaRegistration> registrations = ai4MediaRegs.getRegistrations();

		ClefRegistrations clefRegs = ClefRegistrations.getInstance(registrations);

//		List<ParticipantRegistrationHttpParams> existingRegs = clefRegs.registerNewTeams();
//		clefRegs.updateExistingTeamsTasks(existingRegs);

		clefRegs.registerAndUpdate(); // is the same as above commented two lines
	}

	public static void main(String[] args) throws CsvValidationException, IOException, URISyntaxException,
			InterruptedException, ClefRegistrationSendException {
		test();
	}

}
