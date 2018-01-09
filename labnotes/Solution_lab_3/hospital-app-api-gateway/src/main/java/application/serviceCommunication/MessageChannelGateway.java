package application.serviceCommunication;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.support.MessageBuilder;

import application.registration.Registration;

/**
 * MessageGateways hide the complexity of message channels from the rest of our code. For example:
 *		instead of: producerChannels.send(MessageBuilder.withPayload(registration).build());
 *		we use:     messageGateway.checkIn(registration);
 */
@MessagingGateway
public interface MessageChannelGateway {

	@Gateway(requestChannel = ProducerChannels.CHECK_IN)
	void checkIn(Registration registration);
	
	@Gateway(requestChannel = ProducerChannels.PARKING_VALIDATION)
	void generateParkingCode(Registration registration);	
}