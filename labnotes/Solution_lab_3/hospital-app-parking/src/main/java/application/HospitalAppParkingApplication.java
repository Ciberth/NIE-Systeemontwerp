package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import application.serviceCommunication.Channels;

@SpringBootApplication
@EnableBinding(value={Channels.class})
public class HospitalAppParkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalAppParkingApplication.class, args);
	}
}
