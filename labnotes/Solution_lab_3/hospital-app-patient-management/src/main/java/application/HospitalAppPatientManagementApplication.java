package application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import application.patient.Patient;
import application.patient.PatientRepository;

@SpringBootApplication
public class HospitalAppPatientManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalAppPatientManagementApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner populateRepositories(PatientRepository patientRepository) {
		return (args) -> {
			Patient patient = new Patient("1", "Joe", "Bloggs");
			patientRepository.save(patient);
			Patient patient2 = new Patient("2", "Tim", "Jackson");
			patientRepository.save(patient2);
			Patient patient3 = new Patient("3", "James", "Bloggs");
			patientRepository.save(patient3);
			
			System.out.println(patientRepository.getPaitentsStartingWithJ());
		};
	}
}
