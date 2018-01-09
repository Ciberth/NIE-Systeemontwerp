package be.ugent.intec;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

import be.ugent.intec.commandhandler.InvoicingChannels;
import be.ugent.intec.domainmodel.invoice.Invoice;
import be.ugent.intec.domainmodel.invoice.InvoiceLineItem;
import be.ugent.intec.domainmodel.invoice.PatientInfo;
import be.ugent.intec.domainmodel.invoice.RecipientInfo;
import be.ugent.intec.domainmodel.invoice.RoomInfo;
import be.ugent.intec.persistence.InvoiceRepository;

@EnableBinding(value={InvoicingChannels.class})
@SpringBootApplication
public class InvoicingApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoicingApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner testRepository(InvoiceRepository repo) {
		return (args) -> {
			System.out.println("Running Repository test");
			
			final String patientID = "1-2-3";
			final String patientID2 = "4-5-6";
			
			final PatientInfo patientInfo1 = new PatientInfo(patientID, "John Doe");
			final PatientInfo patientInfo2 = new PatientInfo(patientID2, "Jane Doe");
			
			final Invoice invoice1 = new Invoice("1",
					new RecipientInfo("John Doe", "SomeStreet", RecipientInfo.RecipientType.PATIENT),
					patientInfo1,
					new RoomInfo("", RoomInfo.RoomType.SHARED),
					new ArrayList<InvoiceLineItem>());
			
			final Invoice invoice2 = new Invoice("2",
					new RecipientInfo("John Doe", "SomeStreet", RecipientInfo.RecipientType.PATIENT),
					patientInfo1,
					new RoomInfo("", RoomInfo.RoomType.SHARED),
					new ArrayList<InvoiceLineItem>());
			
			final Invoice invoice3 = new Invoice("3",
					new RecipientInfo("Jane Doe", "SomeStreet2", RecipientInfo.RecipientType.PATIENT),
					patientInfo2,
					new RoomInfo("", RoomInfo.RoomType.SHARED),
					new ArrayList<InvoiceLineItem>());
			
			
			
			repo.deleteAll();
			repo.save(invoice1);
			repo.save(invoice2);
			repo.save(invoice3);
			
			System.out.println("All invoices:");
			repo.findAll().forEach(System.out::println);
			
			System.out.println("Invoices for " + patientID + ":");
			repo.getByPatientId(patientID).forEach(System.out::println);
			
			System.out.println("Unpaid invoices for " + patientID + ":");
			repo.getUnpaidInvoices(patientID).forEach(System.out::println);
			
			invoice2.pay();
			invoice3.pay();
			repo.save(invoice2);
			repo.save(invoice3);
			
			System.out.println("Unpaid invoices for " + patientID + ":");
			repo.getUnpaidInvoices(patientID).forEach(System.out::println);
			
			System.out.println("Find by id: (3)");
			System.out.println(repo.findByInvoiceID("3"));
			
			
		};
	}
	
	@Bean
	public CommandLineRunner testController(InvoiceRepository repo) {
		return (args) -> {
			System.out.println("Controller test");
			
			//Put your controller test code here
		};
	}
}
