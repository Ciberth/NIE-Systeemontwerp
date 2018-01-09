package be.ugent.intec.domainmodel.invoice;

/**
 * Class containing the information regarding the recipient of a specific invoice.
 * 
 * The recipient of an invoice can either be the patient or an insurer. It also contains
 * the address for postage.
 * 
 * @author student
 *
 */
public class RecipientInfo {

	public enum RecipientType{
		PATIENT, INSURER
	}
	
	private final String name;
	private final String address;
	private final RecipientType recipientType;
	
	
	public RecipientInfo(String name, String address, RecipientType recipientType) {
		this.name = name;
		this.address = address;
		this.recipientType = recipientType;
	}


	public String getName() {
		return name;
	}


	public String getAddress() {
		return address;
	}


	public RecipientType getRecipientType() {
		return recipientType;
	}
	
	
	
	
}
