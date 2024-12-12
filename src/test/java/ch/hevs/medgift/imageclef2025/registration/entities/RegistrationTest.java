package ch.hevs.medgift.imageclef2025.registration.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class RegistrationTest {

	@Test
	public void whenGetContactPersonCalled_thenReturnFirstNamePlusLastName() {

		AI4MediaRegistration registration = new AI4MediaRegistration("medgift", "HES-SO VS", "CH", "Ivan Eggel",
				"ivan.eggel@gmail.com", List.of("ImageCLEFmed Caption", "ImageCLEFtoPicto", "ImageCLEFmed MEDVQA"));

		String expected = "Ivan Eggel";
		String actual = registration.person();

		assertEquals(expected, actual);
	}

}
