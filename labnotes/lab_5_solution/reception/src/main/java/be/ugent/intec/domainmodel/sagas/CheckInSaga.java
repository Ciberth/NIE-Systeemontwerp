package be.ugent.intec.domainmodel.sagas;

import be.ugent.intec.adapters.invoicing.InvoicingAdapter;
import be.ugent.intec.adapters.invoicing.InvoicingListener;
import be.ugent.intec.adapters.wardoperations.WardAdapter;

public class CheckInSaga implements InvoicingListener {
	
	private final InvoicingAdapter invoicingAdapter;
	private final WardAdapter wardAdapter;
	private final String patientSSN;
	
	private CheckInSaga(InvoicingAdapter invoicingAdapter, WardAdapter wardAdapter, String patientSSN) {
		this.invoicingAdapter = invoicingAdapter;
		this.wardAdapter = wardAdapter;
		this.patientSSN = patientSSN;
	}

	//Factory method prevents leaking 'this' reference in constructor.
	public static CheckInSaga create(InvoicingAdapter invoicingAdapter, WardAdapter wardAdapter, String patientSSN) {
		CheckInSaga saga = new CheckInSaga(invoicingAdapter, wardAdapter, patientSSN);
		invoicingAdapter.registerInvoicingListener(saga);
		return saga;
	}

	public void start() {
		System.out.println("Check in saga started");
		invoicingAdapter.requestInvoiceValidation(patientSSN);
		//same for ward operations. Then gather reply messages and handle them.
	}

	@Override
	public void onInvoiceValidationReply(boolean accepted) {
		// TODO Auto-generated method stub
		//Handle state transition here. If both ward and invoicing are ok, do check-in
	}


}
