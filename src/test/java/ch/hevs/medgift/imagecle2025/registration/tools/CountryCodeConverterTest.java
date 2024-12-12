package ch.hevs.medgift.imagecle2025.registration.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.opencsv.exceptions.CsvValidationException;

import ch.hevs.medgift.imageclef2025.registration.config.Config;
import ch.hevs.medgift.imageclef2025.registration.tools.CountryCodeConverter;

public class CountryCodeConverterTest {

	@Test
	public void testConversion() throws CsvValidationException, FileNotFoundException, IOException {
		var converter = CountryCodeConverter.getInstance(Config.COUNTRIES_CSV_PATH);
		assertEquals("AF", converter.getIso2("Afghanistan"));
		assertEquals("AF", converter.getIso2("AFGHANISTAN"));
		assertEquals("AF", converter.getIso2("afghanistan"));
		assertEquals("CH", converter.getIso2("Switzerland"));
		assertEquals("RO", converter.getIso2("romania"));
		assertEquals("BQ", converter.getIso2("Bonaire, Sint Eustatius and Saba"));
		assertEquals("BQ", converter.getIso2("bonaire, Sint Eustatius and saba"));
		assertEquals("ZW", converter.getIso2("Zimbabwe"));
	}

}
