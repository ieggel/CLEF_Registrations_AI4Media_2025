package ch.hevs.medgift.imageclef2025.registration.stats;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.opencsv.exceptions.CsvValidationException;

import ch.hevs.medgift.imageclef2025.registration.config.Config;
import ch.hevs.medgift.imageclef2025.registration.entities.AI4MediaRegistration;
import ch.hevs.medgift.imageclef2025.registration.read.AI4MediaRegistrations;

public class BasicRegistrationStats {

	private List<AI4MediaRegistration> registrations;

	private BasicRegistrationStats(List<AI4MediaRegistration> registrations) {
		this.registrations = registrations;
	}

	public static BasicRegistrationStats getInstance(List<AI4MediaRegistration> registrations) {
		return new BasicRegistrationStats(registrations);
	}

	public Map<String, Long> getNbrRegsPerTask() {
		return this.registrations.stream()
				.<String>mapMulti((reg, consumer) -> reg.tasksRegistered().forEach(t -> consumer.accept(t)))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}

	public Map<String, Long> getNbrRegsPerOfficialTask() {

		return this.registrations.stream()
				.collect(Collectors.toMap(reg -> reg.contactEmail(),
						reg -> Config.mapTasksToOfficialTasks(reg.tasksRegistered())))
				.values().stream().flatMap(Collection::stream)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}

	public int getNbrParticipantsTotal() {
		// Actually not necessary to do the distinct() step as shown commented just
		// below, as this should be handled
		// already
		// return this.registrations.stream().map(reg ->
		// reg.contactEmail()).distinct().count();
		return this.registrations.size();
	}

	public List<String> getRegParticipantEmailsForTask(String task) {
		return this.registrations.stream().filter(reg -> reg.tasksRegistered().contains(task))
				.map(reg -> reg.contactEmail()).collect(Collectors.toList());
	}

	public Map<String, List<String>> getRegParticipantEmailsForAllTasks() {
		return Config.TASK_AI4MEDIA_TO_HTTP_PARAM.keySet().stream()
				.collect(Collectors.toMap(task -> task, task -> this.getRegParticipantEmailsForTask(task)));
	}

	public void printNbrRegsPerTask() {
		System.out.println("----------------------");
		System.out.println("Registrations per task: ");
		System.out.println(this.getNbrRegsPerTask());
	}

	public void printNbrRegsPerOfficialTask() {
		System.out.println("----------------------");
		System.out.println("Registrations per official task: ");
		System.out.println(this.getNbrRegsPerOfficialTask());
	}

	public void printNbrParticipantsTotal() {
		System.out.println("----------------------");
		System.out.println("Number of participants in total: ");
		System.out.println(this.getNbrParticipantsTotal());
	}

	public void printRegParticipantEmailsForAllTasks() {
		System.out.println("----------------------");
		System.out.println("Registered Participants' emails for each task:");
		this.getRegParticipantEmailsForAllTasks().entrySet().forEach(entry -> {
			System.out.printf("Task: %-25s Registered emails: %s\n", entry.getKey(), entry.getValue());
			// System.out.printf("%-15s hello\n", "haha");
		});
	}

	public void printParticipantsAndTasks() {
		System.out.println("----------------------");
		this.registrations.forEach(reg -> {
			System.out.println(String.format("Paticipant %s: %s", reg.contactEmail(), reg.tasksRegistered()));
		});
	}

	public void printParticipantsAndTasksDetailedJson() {
		System.out.println("----------------------");
		System.out.print("{\"participants\":[");
		var output = new StringBuilder();
		this.registrations.forEach(reg -> {
			var tasks = new StringBuilder();
			reg.tasksRegistered().forEach(task -> {
				tasks.append(String.format("\"%s\",", task));
			});
			// Remove comma
			if (tasks.length() > 1)
				tasks.setLength(tasks.length() - 1);

			output.append(String.format(
					"{\"email\":\"%s\",\"team\":\"%s\",\"name\":\"%s\","
							+ "\"affiliation\":\"%s\",\"country\":\"%s\",\"tasks\":[%s]},",
					reg.contactEmail(), reg.teamName(), reg.person(), reg.affiliation(), reg.countryCode(), tasks));

		});

		if (output.length() > 1)
			output.setLength(output.length() - 1);

		output.append("]}");
		System.out.println(output);
	}

	public static void main(String[] args) throws CsvValidationException, FileNotFoundException, IOException,
			URISyntaxException, InterruptedException {
		AI4MediaRegistrations ai4MediaRegs = AI4MediaRegistrations.getInstance(Config.URL_AI4MEDIA_REGS);
		List<AI4MediaRegistration> registrations = ai4MediaRegs.getRegistrations();
		BasicRegistrationStats stats = BasicRegistrationStats.getInstance(registrations);
//		stats.printParticipantsAndTasks();
		stats.printParticipantsAndTasksDetailedJson();
//		stats.printNbrRegsPerTask();
//		stats.printNbrRegsPerOfficialTask();
//		stats.printNbrParticipantsTotal();
//		stats.printRegParticipantEmailsForAllTasks();

	}

}
