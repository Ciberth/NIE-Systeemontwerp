package be.ugent.intec;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

	String INVOICE_VALIDATION_REQUEST_CHANNEL = "InvoiceValidationRequest";
	String INVOICE_VALIDATION_REPLY_CHANNEL   = "InvoiceValidationReply";
	
	//final String INVOICE_CREATION_REQUEST_CHANNEL   = "InvoiceCreationRequest";
	//final String INVOICE_CREATION_REPLY_CHANNEL     = "InvoiceCreationReply";

	@Output(INVOICE_VALIDATION_REQUEST_CHANNEL)
	MessageChannel invoiceValidationRequest();
	
	@Input(INVOICE_VALIDATION_REPLY_CHANNEL)
	SubscribableChannel invoiceValidationReply();
	
	//@Output(INVOICE_CREATION_REQUEST_CHANNEL)
	//MessageChannel invoiceCreationRequest();

	//@Input(INVOICE_CREATION_REPLY_CHANNEL)
	//SubscribableChannel invoiceCreationReply();
	
}
