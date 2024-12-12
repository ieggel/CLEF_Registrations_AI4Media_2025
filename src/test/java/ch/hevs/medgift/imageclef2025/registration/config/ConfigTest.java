package ch.hevs.medgift.imageclef2025.registration.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ConfigTest {

	@Test
	public void whenMapTasksToOfficialTasksCalled_returnCorrectResult() {

		var tasks = new ArrayList<String>(Config.TASK_AI4MEDIA_TO_HTTP_PARAM.keySet());

		var expected = List.of(Config.MEDICAL_TASK, Config.TOUCHE_ARGUMENT_TASK, Config.TOPICTO_TASK,
				Config.MULTIOMODAL_REASON_TASK);

		var actual = Config.mapTasksToOfficialTasks(tasks);

		assertTrue(expected.size() == actual.size() && actual.containsAll(expected));

	}

}
