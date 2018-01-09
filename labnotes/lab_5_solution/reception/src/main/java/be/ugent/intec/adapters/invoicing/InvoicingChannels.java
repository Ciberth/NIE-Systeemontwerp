package be.ugent.intec.adapters.invoicing;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface InvoicingChannels {

	final String INVOICE_VALIDATION_REQUEST_CHANNEL = "InvoiceValidationRequestChannel";
	final String INVOICE_VALIDATION_REPLY_CHANNEL = "InvoiceValidationReplyChannel";
	
	@Output(INVOICE_VALIDATION_REQUEST_CHANNEL)
	MessageChannel invoiceValidationRequest();
	
	@Input(INVOICE_VALIDATION_REPLY_CHANNEL)
	SubscribableChannel invoiceValidationReply();
	
	//...
	
}
