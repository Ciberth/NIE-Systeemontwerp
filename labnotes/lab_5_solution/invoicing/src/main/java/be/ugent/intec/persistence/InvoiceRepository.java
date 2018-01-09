package be.ugent.intec.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import be.ugent.intec.domainmodel.invoice.Invoice;

/**
 * CrudRepository to store Invoices
 * 
 * Implemented as a MongoDB store since the variable lengths InvoiceLineItems field can be
 * more efficiently mapped to a document than an SQL table.
 * 
 * @author student
 *
 */
public interface InvoiceRepository extends CrudRepository<Invoice, Long>{

	@Query("{paid : false, patientInfo.ssn : ?0}")
	List<Invoice> getUnpaidInvoices(String patientSSN);
	
	@Query("{patientInfo.ssn : ?0}")
	List<Invoice> getByPatientId(String patientSSN);
	
	Invoice findByInvoiceID(String invoiceId);
}
