package be.ugent.intec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;


@EnableBinding(value={Channels.class})
@SpringBootApplication
public class InvoicingTesterApplication {

	@Autowired 
	private MessageChannelGateway gateway;
	
	public static void main(String[] args) {
		SpringApplication.run(InvoicingTesterApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner testRepository(InvoiceControllerTester tester) {
		return (args) -> {
			//Repeat test since messages could be missed
			while(true) {
				//Sending message should be done through gateway. Receiving can be through InvoiceingControllerTester.
				//@SendTO does not get triggered if you just invoke the method.
				//Request new invoice for patient with open invoice (return false) and a patient with only paid invoices (return true)
				System.out.println("Sending request");
				gateway.validateInvoice("1-2-3");
				
				gateway.validateInvoice("4-5-6");
				Thread.sleep(5000);
			}
		};
	}
}
