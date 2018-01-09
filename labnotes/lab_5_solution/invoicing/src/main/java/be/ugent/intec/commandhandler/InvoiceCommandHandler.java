package be.ugent.intec.commandhandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import be.ugent.intec.domainmodel.InvoiceController;
import be.ugent.intec.domainmodel.invoice.Invoice;
import be.ugent.intec.domainmodel.invoice.RoomInfo;

/**
 * Message handler that controls the {@link InvoiceController} from a Kafka queue.
 * 
 * @author student
 *
 */
@Component
public class InvoiceCommandHandler {

	@Autowired
	private InvoiceController invoiceController;
	
	@StreamListener(InvoicingChannels.INVOICE_VALIDATION_REQUEST_CHANNEL)
	@SendTo(InvoicingChannels.INVOICE_VALIDATION_REPLY_CHANNEL)
	public String validateInvoice(String patientSSN) {
		System.out.println("Incomming validation request...");
		JSONObject ret = new JSONObject();
		try {
			return ret.put("patientSSN", patientSSN ).put("reply", this.invoiceController.isNewInvoiceAccpeted(patientSSN)).toString();
		}catch(JSONException e) {
			//In a real application the handling will be done properly.
			e.printStackTrace();
			return null;
		}
	}
	
	
	@StreamListener(InvoicingChannels.INVOICE_CREATION_REQUEST_CHANNEL)
	@SendTo(InvoicingChannels.INVOICE_CREATION_REPLY_CHANNEL)
	public String createInvoice(String patientSSN, String patientName, String patientAddress, String roomNumber, boolean shared) {
		System.out.println("Invoice creation request...");
		Invoice invoice = invoiceController.createPatientInvoice(patientSSN, patientName, patientAddress, roomNumber,
				shared ? RoomInfo.RoomType.SHARED : RoomInfo.RoomType.SINGLE);
		JSONObject ret = new JSONObject();
		try {
			ret.put("patientSSN", patientSSN);
			ret.put("invoiceID", invoice.getInvoiceID());
			return ret.toString();
		}catch(JSONException e) {
			//In a real application the handling will be done properly.
			return null;
		}
	}
	
	//... other methods can be added here
	
}
