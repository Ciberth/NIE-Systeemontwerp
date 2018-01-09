package be.ugent.intec;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;


@Service
public class InvoiceControllerTester {

	public InvoiceControllerTester() {
		
	}
	
	@StreamListener(Channels.INVOICE_VALIDATION_REPLY_CHANNEL)
	public void processReply(String reply) {
		System.out.println("Invoice reply: " + reply);
	}
}
