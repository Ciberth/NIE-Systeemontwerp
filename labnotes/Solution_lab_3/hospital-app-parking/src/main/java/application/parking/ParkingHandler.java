package application.parking;


import java.io.IOException;
import java.util.Random;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.serviceCommunication.Channels;

@Component
public class ParkingHandler {
	
	/**
	 * Print the PATIENT_REGISTRATION channel's message and send a random number to the PARKING_CODE channel
	 * @param message
	 * @return
	 */
	@StreamListener(Channels.PATIENT_REGISTRATION)
	@SendTo(Channels.PARKING_CODE)
    public int generateParkingCode(String message) {
		System.out.println("Parking application recieved: " + message); 
				
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);			
		try {
			// Deserialize the message to a class
			//Example example = objectMapper.readValue(message, Example.class);
			
			// we could also get individual parameters:
			String patient = objectMapper.readTree(message).get("patient").asText();
			System.out.println(patient);
			
			// Do something with the deserialize message
			// ...			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		Random rand = new Random();
	    return rand.nextInt((10 - 0) + 1); // return a random number between 0 and 10
    }
}
