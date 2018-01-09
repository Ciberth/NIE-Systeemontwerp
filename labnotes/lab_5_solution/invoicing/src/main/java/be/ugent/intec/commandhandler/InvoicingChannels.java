package be.ugent.intec.commandhandler;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface InvoicingChannels {

	final String INVOICE_VALIDATION_REQUEST_CHANNEL = "InvoiceValidationRequest";
	final String INVOICE_VALIDATION_REPLY_CHANNEL   = "InvoiceValidationReply";
	
	final String INVOICE_CREATION_REQUEST_CHANNEL   = "InvoiceCreationRequest";
	final String INVOICE_CREATION_REPLY_CHANNEL     = "InvoiceCreationReply";

	@Input(INVOICE_VALIDATION_REQUEST_CHANNEL)
	SubscribableChannel invoiceValidationRequest();
	
	@Output(INVOICE_VALIDATION_REPLY_CHANNEL)
	MessageChannel invoiceValidationReply();
	
	@Input(INVOICE_CREATION_REQUEST_CHANNEL)
	SubscribableChannel invoiceCreationRequest();

	@Output(INVOICE_CREATION_REPLY_CHANNEL)
	MessageChannel invoiceCreationReply();
}
