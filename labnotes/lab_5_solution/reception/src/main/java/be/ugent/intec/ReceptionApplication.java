package be.ugent.intec;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import be.ugent.intec.domainmodel.HospitalBooking;
import be.ugent.intec.persistence.HospitalBookingRepository;

@SpringBootApplication
public class ReceptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReceptionApplication.class, args);
	}
	

	@Bean
	public CommandLineRunner testRepository(HospitalBookingRepository repo) {
		return (args) -> {
			HospitalBooking booking1 = new HospitalBooking("patientSSN1");
			HospitalBooking booking2 = new HospitalBooking("patientSSN1");
			HospitalBooking booking3 = new HospitalBooking("patientSSN2");
			
			repo.save(booking1);
			repo.save(booking2);
			repo.save(booking3);
			
			repo.findAll().forEach(System.out::println);
		};
	}
}
