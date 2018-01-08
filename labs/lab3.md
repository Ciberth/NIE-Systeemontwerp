# Lab 3:

## Stuff in assignment

### Kafka

> Run zookeeper before kafka!

[Quickstart](https://kafka.apache.org/quickstart)

Quick and dirty:

1. ``bin/zookeeper-server-start.sh config/zookeeper.properties``
2. ``bin/kafka-server-start.sh config/server.properties``

### Communication using REST

- Nothing more needs to be done with kafka.
- Everything in spring boot
- Pom.xml needs to have right dependencies!
- In _application.properties_ add ``server.port=2222`` line (and other properties)!
- Create the most important class like _patient_, _invoice_, _person_, _game_...
- Create Repository (interface that extends CrudRepository) with the Queries.
- Create Controller **RestController** annotation, **Autowired** annotation for repository field and **RequestMapping** for the REST calls. 
- In application itself you can create a **Bean** with **CommandLineRunner** to populate repository. 


**pom.xml stuff:**

* Parent tags:
	- spring-boot-starter-parent (bij parent tags)
* Dependency tags:
	- spring-boot-starter
	- spring-boot-starter-test
	- spring-boot-starter-web
	- spring-boot-starter-data-jpa
	- com.h2database
	- mongo
	- sql
* Build tag:
	- spring-boot-maven-plugin

### Communication using message channels

TODO zelf eerst eens uittesten



## Stuff from solution REST = Patient Management

### Patient.java

```java

@Entity
public class Patient { //extends BaseEntity
	@Id
	private String ssn;//socialSecurityNumber
	
	private String firstname;
	private String lastname;

	public Patient() { }
	
	public Patient(String ssn) { 
		this.ssn = ssn;
	}
	
	public Patient(String ssn, String firstname, String lastname) { 
		this.ssn = ssn;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	// Getters & setters	
	
}

```


### PatientRepository.java

```java

public interface PatientRepository extends CrudRepository<Patient, String> {

	@Query("SELECT p FROM Patient p WHERE (p.firstname LIKE 'J%')")
	List<Patient> getPaitentsStartingWithJ();
}

```

### PatientController.java

```java

@RestController
public class PatientController {
	
	@Autowired 
	private PatientRepository patientRepository;

	@RequestMapping(value="/patient", method=RequestMethod.GET)
	public Patient registerPatientForStay(@RequestParam("id") String patientSSN) {
		System.out.println("registerPatientForStay() recieved: " + patientSSN);		
		Patient patient = patientRepository.findOne(patientSSN);		
	    return patient; 
	}
}

```

### PatientManagementApplication.java

```java
@SpringBootApplication
public class HospitalAppPatientManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalAppPatientManagementApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner populateRepositories(PatientRepository patientRepository) {
		return (args) -> {
			Patient patient = new Patient("1", "Joe", "Bloggs");
			patientRepository.save(patient);
			Patient patient2 = new Patient("2", "Tim", "Jackson");
			patientRepository.save(patient2);
			Patient patient3 = new Patient("3", "James", "Bloggs");
			patientRepository.save(patient3);
			
			System.out.println(patientRepository.getPaitentsStartingWithJ());
		};
	}
}
```

### application.properties

```
server.port=2224
```


### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.example</groupId>
	<artifactId>hospital-app-patient-management</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>hospital-app-patient-management</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.8.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency> <!-- default: added to all spring projects -->
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		
		<dependency> <!-- allows the "org.springframework.web" imports e.g. get/post controller -->
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
       
	    
	    
	    <dependency> <!-- entities -->
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
        </dependency>
        
        
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>


</project>

```




## Stuff from solution message channels = Parking


### ParkingHandler.java

```java

@Component
public class ParkingHandler {
	
	@StreamListener(Channels.PATIENT_REGISTRATION)
	@SendTo(Channels.PARKING_CODE)
    public int generateParkingCode(String message) {
		System.out.println("Parking application recieved: " + message); 
				
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);			
		try {
			// Deserialize the message to a class
			//Example example = objectMapper.readValue(message, Example.class);
			
			// we could also get individual parameters:
			String patient = objectMapper.readTree(message).get("patient").asText();
			System.out.println(patient);
			
			// Do something with the deserialize message
			// ...			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		Random rand = new Random();
	    return rand.nextInt((10 - 0) + 1); // return a random number between 0 and 10
    }
}

```

### Channels.java (interface)

```java

public interface Channels {
	String PATIENT_REGISTRATION = "patient_registration";
	String PARKING_CODE = "parking_code";
	
	@Input(PATIENT_REGISTRATION)
	SubscribableChannel patientRegistration();	
	
	@Output(PARKING_CODE)
	MessageChannel parkingCode();

}
```


### ParkingApplication.java

```java

@SpringBootApplication
@EnableBinding(value={Channels.class}) // <------ !!
public class HospitalAppParkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalAppParkingApplication.class, args);
	}
}

```

### application.properties

```
#output channels
spring.cloud.stream.bindings.parking_code.destination=parking_code
spring.cloud.stream.bindings.parking_code.contentType=application/json
spring.cloud.stream.bindings.output.binder=kafka

#input channels
spring.cloud.stream.bindings.patient_registration.destination=parking_check_in
spring.cloud.stream.bindings.patient_registration.contentType=application/json
spring.cloud.stream.bindings.input.binder=kafka

server.port=2221```



### pom.xml

```xml
<!-- For messages: -->
        
    	
    	<dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-stream-binder-kafka</artifactId>
                <version>1.3.0.RELEASE</version>
		</dependency>
		<dependency>
		    <groupId>com.esotericsoftware</groupId>
		    <artifactId>kryo</artifactId>
		    <version>4.0.1</version>
		</dependency>
```









TODO

- check die properties van kafka of da custom is of default
- vul dependency tags van mongo en sql aan hierboven bij pom.xml

## URLS

[Spring cloud stream](https://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/)