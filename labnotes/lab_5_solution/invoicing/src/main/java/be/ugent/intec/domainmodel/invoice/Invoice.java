package be.ugent.intec.domainmodel.invoice;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import be.ugent.intec.domainmodel.InvoiceCalculationService;

/**
 * Class modeling an invoice of a patient.
 * 
 * An invoice manages the list of charged items (InvoiceLineItems) as well as information
 * about the patient, the recipient of the invoice (patient or insurer) and the room
 * associated with this hospital stay. The combination of this information can be used to
 * calculate the total due amount. This logic is deferred to the {@link InvoiceCalculationService}
 * 
 * @author student
 *
 */
@Document(collection="Invoice")
public class Invoice {
	@Id
	private String invoiceID;
	private final RecipientInfo recipientInfo;
	private final PatientInfo patientInfo;
	private final RoomInfo roomInfo;
	private final List<InvoiceLineItem> invoiceLineItems;
	private boolean paid;
	
	public Invoice(String invoiceID, RecipientInfo recipientInfo, PatientInfo patientInfo, RoomInfo roomInfo,
			List<InvoiceLineItem> invoiceLineItems) {
		this.invoiceID = invoiceID;	
		this.recipientInfo = recipientInfo;
		this.patientInfo = patientInfo;
		this.roomInfo = roomInfo;
		this.invoiceLineItems = invoiceLineItems;
	}

	public String getInvoiceID() {
		return invoiceID;
	}

	public RecipientInfo getRecipientInfo() {
		return recipientInfo;
	}

	public PatientInfo getPatientInfo() {
		return patientInfo;
	}

	public RoomInfo getRoomInfo() {
		return roomInfo;
	}

	public List<InvoiceLineItem> getInvoiceLineItems() {
		return new ArrayList<InvoiceLineItem>(invoiceLineItems);
	}
	
	public void addInvoiceLineItem(String name, double price, int quantity) throws InvoiceClosedException {
		if( ! paid) {
			this.invoiceLineItems.add(new InvoiceLineItem(name, price, quantity));
		}else {
			throw new InvoiceClosedException();
		}
	}
	
	public boolean isPaid() {
		return paid;
	}
	
	public void pay() {
		this.paid = true;
	}
	
	@Override
	public String toString() {
		return MessageFormat.format(
				"Invoice {0}: " 
				+ "\n\t paid: {1}"
				+ "\n\t patient: {2}",
		
				this.invoiceID,
				this.paid,
				this.patientInfo
				);
	}
	
}
