package be.ugent.intec.adapters.invoicing;

public interface InvoicingListener {

	void onInvoiceValidationReply(boolean accepted);
}
