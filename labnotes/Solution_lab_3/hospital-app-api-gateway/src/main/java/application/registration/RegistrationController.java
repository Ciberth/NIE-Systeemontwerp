package application.registration;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
//import org.springframework.integration.dsl.IntegrationFlow;
//import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.serviceCommunication.ConsumerChannels;
import application.serviceCommunication.MessageChannelGateway;


/**
 * Displays the registration form a and result to the user.
 * 	The registration result is populated using web sockets, when the other services publish the relevant information
 * 
 *
 */
@Controller 
public class RegistrationController {

	private RestTemplate restTemplate = new RestTemplate(); // used to get the patients details
	private String patientUrl = "http://localhost:2224/patient?id={param}"; // we could get this using service discovery 	
	
	private MessageChannelGateway messagingGateway; // used to send messages to other services via kafka
	
    @Autowired 
	private RegistrationRepository registrationRepository;
	
    @Autowired 
    private SimpMessagingTemplate simpMessagingTemplate; // used to send messages to the client (websockets)
	
    @Autowired 
    RegistrationController(MessageChannelGateway messagingGateway){
    	this.messagingGateway = messagingGateway;
    }
    
	
	@GetMapping("/registration_form")
    public String newCustomer(Model model) {   		
		model.addAttribute("registration", new Registration()); 
        return "registration_form";
    }
	
    @PostMapping("/registration") // called after the registration form has been submitted
    public String registrationSubmit(@ModelAttribute Registration registration, Model model) {
    	registrationRepository.save(registration);
    	
    	String patient = getPatientDetails(registration.getPatient());
    	//String patient = "{\"ssn\":\"1\"}";
    	
    	model.addAttribute("patient", patient);
    	model.addAttribute("registrationId", registration.getId());
	 	
       return "registration_result";
    }
    
    /**
     * Perform REST request to get the patient's details
     * @param ssn
     * @return
     */
    private String getPatientDetails(String ssn){
    	String response = restTemplate.getForObject(patientUrl, String.class, ssn);
    	System.out.println("REST get patient response: " + response);
    	return response;
    }
    
    /**
     *  When the client has connected, ask for the parking code and patient check in 
     * @param registrationId
     */
    @MessageMapping(value="/connected")
    public void clientConnected(Long registrationId) { 	
    	System.out.println("client for registrationId " + registrationId + " has connected to web socket.");
    	
    	Registration registration = registrationRepository.findOne(registrationId);
    	
    	// publish the registration to kafka topics
    	this.messagingGateway.generateParkingCode(registration);
    	 
    }
    
    @MessageMapping(value="/confirm")
    public void confirmRegistration(Long registrationId) { 	
    	System.out.println("client for registrationId " + registrationId + " has been confirmed.");
    	Registration registration = registrationRepository.findOne(registrationId);
    	this.messagingGateway.checkIn(registration); 
    	 
    }
    /*
    @RequestMapping(value = "/confirm_registration", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void confirmRegistration(@RequestParam Long registrationId) { //
    	System.out.println("client for registrationId " + registrationId + " has been confirmed.");
    	Registration registration = registrationRepository.findOne(registrationId);
    	this.messagingGateway.checkIn(registration); 
    }*/
    
    
    /**
     * Send the parking code to the client
     * @param parkingCode
     */
    @StreamListener(ConsumerChannels.PARKING_CODE)
    public void showParkingCode(int parkingCode) {
    	// This is just used to display the output from the services
    		// 	In a real system you should use authentication and just send the message to a specific user!
    	simpMessagingTemplate.convertAndSend("/topic/parking_code", parkingCode);	
    }
    
    /**
     * Send the invoice and bed assignment details to the client
     * @param hospitalStay
     */
    @StreamListener(ConsumerChannels.HOSPITAL_STAY)
    public void showCheckInDetails(String hospitalStay) {
		System.out.println("Api gateway recieved hospitalStay details: " + hospitalStay); 
    	// This is just used to display the output from the services
			// 	In a real system you should use authentication and just send the message to a specific user!
    	simpMessagingTemplate.convertAndSend("/topic/registration_details", hospitalStay);	
    }
}

