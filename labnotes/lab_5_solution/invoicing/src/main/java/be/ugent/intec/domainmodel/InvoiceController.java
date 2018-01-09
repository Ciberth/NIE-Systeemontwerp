package be.ugent.intec.domainmodel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.ugent.intec.domainmodel.invoice.Invoice;
import be.ugent.intec.domainmodel.invoice.InvoiceClosedException;
import be.ugent.intec.domainmodel.invoice.InvoiceLineItem;
import be.ugent.intec.domainmodel.invoice.PatientInfo;
import be.ugent.intec.domainmodel.invoice.RecipientInfo;
import be.ugent.intec.domainmodel.invoice.RoomInfo;
import be.ugent.intec.persistence.InvoiceRepository;

@Component
public class InvoiceController {

	@Autowired
	private InvoiceCalculationService invoiceCalculationService;
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	/**
	 * Method that checks if a new invoice can be opened for a specific patient.
	 * 
	 * A new invoice can only be opened if the patient has no unpaid invoices.
	 * 
	 * @param patientSSN: Social security number of the patient.
	 * @return true if an invoice can be opened, false otherwise.
	 */
	public boolean isNewInvoiceAccpeted(String patientSSN) {
		List<Invoice> unpaidInvoices = invoiceRepository.getUnpaidInvoices(patientSSN);
		return unpaidInvoices.isEmpty();
	}

	
	public double getInvoiceTotalDueAmount(String invoiceID) {
		return this.invoiceCalculationService.calculateTotalDueAmount(invoiceID);
	}
	
	public void payInvoice(String patientSSN) {
		List<Invoice> invoices = invoiceRepository.getUnpaidInvoices(patientSSN);
		
		invoices.forEach(Invoice::pay);
	}
	
	public void chargeItem(String invoiceId, String name, double price, int quantity) throws InvoiceClosedException{
		Invoice invoice = this.invoiceRepository.findByInvoiceID(invoiceId);
		invoice.addInvoiceLineItem(name, price, quantity);
		this.invoiceRepository.save(invoice);
	}
	
	public Invoice createPatientInvoice(String patientSSN, String patientName, String patientAddress, String roomNumber, RoomInfo.RoomType roomType) {
		final String invoiceID = LocalDateTime.now().toString();
		final RecipientInfo recipientInfo = new RecipientInfo(patientName, patientAddress, RecipientInfo.RecipientType.PATIENT);
		final PatientInfo patientInfo = new PatientInfo(patientSSN, patientName);
		final RoomInfo roomInfo = new RoomInfo(roomNumber, roomType);
		final Invoice invoice = new Invoice(invoiceID, recipientInfo, patientInfo, roomInfo, new ArrayList<InvoiceLineItem>(10));
		this.invoiceRepository.save(invoice);
		return invoice;
	}
}
