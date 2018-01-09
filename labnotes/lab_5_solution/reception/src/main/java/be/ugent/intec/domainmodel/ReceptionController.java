package be.ugent.intec.domainmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.ugent.intec.adapters.invoicing.InvoicingAdapter;
import be.ugent.intec.adapters.wardoperations.WardAdapter;
import be.ugent.intec.domainmodel.sagas.CheckInSaga;

@Component
public class ReceptionController {

	@Autowired
	private InvoicingAdapter invoicingAdapter;
	
	@Autowired
	private WardAdapter wardAdapter;
	
	public void makeBooking(String patientSSN) {
		//...
		System.out.println("making booking for patient: " + patientSSN);
	}
	
	public void cancelBooking() {
		//...
	}
	
	public void checkIn(String patientSSN) {
		//In realitiy this should be based on the booking
		CheckInSaga saga = CheckInSaga.create(invoicingAdapter, wardAdapter, patientSSN);
		saga.start();
	}
	
	public void checkOut() {
		//..
	}
	
	//...
}
