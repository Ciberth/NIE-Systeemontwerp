package be.ugent.intec.commandhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.ugent.intec.domainmodel.ReceptionController;

@RestController
public class ReceptionCommandHandler {

	@Autowired
	private ReceptionController receptionController;
	
	
	@RequestMapping(value="/book", method=RequestMethod.GET)
	public void book(@RequestParam("id") String patientSSN ) {
		System.out.println("Got booking request");
		//dummy handling
		this.receptionController.makeBooking(patientSSN);
	}
	
	@RequestMapping(value="/checkIn",  method=RequestMethod.GET)
	public void checkIn(@RequestParam("id") String patientSSN) {
		System.out.println("CHeck in " + patientSSN);
		this.receptionController.checkIn(patientSSN);
	}
	
	//...
}
