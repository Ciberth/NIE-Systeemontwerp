package application.patient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class PatientController {
	
	@Autowired 
	private PatientRepository patientRepository;

	/**
	 * Query the repository for the patient 
	 * @param patientSSN primary key for the patient table (i.e. social security number)
	 * @return the patient
	 */
	@RequestMapping(value="/patient", method=RequestMethod.GET)
	public Patient registerPatientForStay(@RequestParam("id") String patientSSN) {
		System.out.println("registerPatientForStay() recieved: " + patientSSN);		
		Patient patient = patientRepository.findOne(patientSSN);
		
	    return patient; 
	}
}
