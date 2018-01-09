package be.ugent.intec;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;


@MessagingGateway
public interface MessageChannelGateway {

	@Gateway(requestChannel = Channels.INVOICE_VALIDATION_REQUEST_CHANNEL)
	void validateInvoice(String patientSSN);
	
}
