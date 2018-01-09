package be.ugent.intec.adapters.invoicing;


import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class InvoicingAdapter {
	
	private final Set<InvoicingListener> invoicingListeners = new HashSet<>(5);
	
	@SendTo(InvoicingChannels.INVOICE_VALIDATION_REQUEST_CHANNEL)
	public String requestInvoiceValidation(String patientSSN) {
		return patientSSN;
	}
	
	public boolean registerInvoicingListener(InvoicingListener listener) {
		synchronized(this.invoicingListeners) {
			return this.invoicingListeners.add(listener);
		}
	}
	
	@StreamListener(InvoicingChannels.INVOICE_VALIDATION_REPLY_CHANNEL)
	public void onInvoiceValidationReply(String replyString) {
		JSONObject reply;
		try {
			reply = new JSONObject(replyString);
			final boolean validated = reply.getBoolean("reply");
			synchronized(this.invoicingListeners) {
				this.invoicingListeners.forEach(listener -> listener.onInvoiceValidationReply(validated));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
