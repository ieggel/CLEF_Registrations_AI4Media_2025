package ch.hevs.medgift.imageclef2025.registration.config;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class AI4MediaFieldsText {

	@Test
	public void shouldReturnFieldNamesByKey() {

		String[] expected = { "person", "country", "affiliation", "team", "tasks", "email" };
		String[] actual = new String[expected.length];

		var keys = AI4MediaFields.getKeys();

		for (int i = 0; i < keys.length; i++) {
			actual[i] = AI4MediaFields.getName(keys[i]);
		}

		Arrays.sort(expected);
		Arrays.sort(actual);

		assertArrayEquals(expected, actual);
		assertThrows(IllegalArgumentException.class, () -> {
			AI4MediaFields.getName("whatever");
		});

	}

}
