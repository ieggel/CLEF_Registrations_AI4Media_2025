package ch.hevs.medgift.imageclef2025.registration.entities;

import java.util.List;

public record AI4MediaRegistration(String teamName, String affiliation, String countryCode, String person,
		String contactEmail,
		// Names from columns in GSheet
		List<String> tasksRegistered) {

}
