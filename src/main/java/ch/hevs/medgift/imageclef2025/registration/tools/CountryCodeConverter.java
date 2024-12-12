package ch.hevs.medgift.imageclef2025.registration.tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class CountryCodeConverter {

	private String countriesCsvPath;
	private Map<String, String> countryNameToIso2 = new HashMap<>();

	private CountryCodeConverter(String countriesCsvPath)
			throws CsvValidationException, FileNotFoundException, IOException {
		this.countriesCsvPath = countriesCsvPath;
		this.readCountries();
	}

	public static CountryCodeConverter getInstance(String countriesCsvPath)
			throws CsvValidationException, FileNotFoundException, IOException {
		return new CountryCodeConverter(countriesCsvPath);
	}

	public String getIso2(String countryName) {
		return this.countryNameToIso2.get(countryName.toLowerCase());
	}

	private void readCountries() throws CsvValidationException, FileNotFoundException, IOException {

		final InputStream is = getClass().getClassLoader().getResourceAsStream(countriesCsvPath);

		try (final CSVReader csvReader = new CSVReader(new InputStreamReader(is))) {
			String[] line;
			csvReader.readNext(); // Skip header

			while ((line = csvReader.readNext()) != null) {
				final String countryName = line[0].toLowerCase();
				final String iso2 = line[1];
				this.countryNameToIso2.put(countryName, iso2);
			}
		}
	}
}
