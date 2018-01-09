package application.serviceCommunication;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * Defines are Input channels
 *
 */
public interface ConsumerChannels {
	String PARKING_CODE = "parking_code";
	String HOSPITAL_STAY = "hospital_stay";
	
	@Input(PARKING_CODE)
	SubscribableChannel parkingCode();

	
	@Input(HOSPITAL_STAY)
	SubscribableChannel hospitalStay();

}