package application.serviceCommunication;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * Defines are Input and Output channels
 *
 */
public interface Channels {
	String PATIENT_REGISTRATION = "patient_registration";
	String PARKING_CODE = "parking_code";
	
	@Input(PATIENT_REGISTRATION)
	SubscribableChannel patientRegistration();	
	
	@Output(PARKING_CODE)
	MessageChannel parkingCode();

}