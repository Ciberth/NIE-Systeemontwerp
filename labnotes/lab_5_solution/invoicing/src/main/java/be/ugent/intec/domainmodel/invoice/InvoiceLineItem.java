package be.ugent.intec.domainmodel.invoice;

/**
 * Class modeling a line on an invoice.
 * 
 * A line on an invoice represents a single charged item or service.
 * 
 * @author student
 *
 */
public class InvoiceLineItem {

	private final String name;
	private final double unitPrice;
	private final int quantity;
	
	
	public InvoiceLineItem(String name, double unitPrice, int quantity) {
		this.name = name;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}


	public String getName() {
		return name;
	}


	public double getUnitPrice() {
		return unitPrice;
	}


	public int getQuantity() {
		return quantity;
	}
	
	
	
}
