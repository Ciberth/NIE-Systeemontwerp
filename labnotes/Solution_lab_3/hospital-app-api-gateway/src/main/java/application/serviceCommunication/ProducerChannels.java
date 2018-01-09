package application.serviceCommunication;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Defines are Output channels
 *
 */
public interface ProducerChannels {
	String CHECK_IN = "check_in";
	String PARKING_VALIDATION = "validate_parking";
	
	@Output(CHECK_IN)
	MessageChannel checkIn();
	
	@Output(PARKING_VALIDATION)
	MessageChannel validateParking();
}
