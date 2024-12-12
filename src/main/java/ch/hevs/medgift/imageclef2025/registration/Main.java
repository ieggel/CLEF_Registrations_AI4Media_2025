package ch.hevs.medgift.imageclef2025.registration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.exceptions.CsvValidationException;

import ch.hevs.medgift.imageclef2025.registration.config.Config;
import ch.hevs.medgift.imageclef2025.registration.entities.AI4MediaRegistration;
import ch.hevs.medgift.imageclef2025.registration.read.AI4MediaRegistrations;
import ch.hevs.medgift.imageclef2025.registration.send.ClefRegistrationSendException;
import ch.hevs.medgift.imageclef2025.registration.send.ClefRegistrations;

public class Main {

	public static void main(String[] args) throws CsvValidationException, FileNotFoundException, IOException,
			URISyntaxException, InterruptedException, ClefRegistrationSendException {

		configureLoggers();

		AI4MediaRegistrations gformRegs = AI4MediaRegistrations.getInstance(Config.URL_AI4MEDIA_REGS);
		List<AI4MediaRegistration> registrations = gformRegs.getRegistrations();

		ClefRegistrations clefRegs = ClefRegistrations.getInstance(registrations);

		clefRegs.registerAndUpdate();

	}

	private static void configureLoggers() {
		Logger rootLogger = Logger.getLogger("");
		Handler[] loggerHandlers = rootLogger.getHandlers();

		// What is shown
		Arrays.stream(loggerHandlers).filter(h -> h instanceof ConsoleHandler).findFirst()
				.ifPresent(h -> h.setLevel(Level.FINE));

		Logger projectMainLogger = Logger.getLogger(Config.MAIN_LOGGER_NAME);
		// What is logged
		projectMainLogger.setLevel(Level.ALL);

	}

}
