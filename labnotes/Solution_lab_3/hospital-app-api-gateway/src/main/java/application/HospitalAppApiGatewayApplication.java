package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import application.serviceCommunication.ConsumerChannels;
import application.serviceCommunication.ProducerChannels;

/**
 * hostpital-app-api-gateway: Communicates with the client and the BackEnd services.
 * 
 * 
 * "The @SpringBootApplication annotation is equivalent to using @Configuration, @EnableAutoConfiguration and @ComponentScan with their default attributes."
 * https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-using-springbootapplication-annotation.html
 * 
 * "@EnableBinding is parameterized by an interface (in this case Source) which declares input and output channels. "
 * http://cloud.spring.io/spring-cloud-stream/spring-cloud-stream.html
 * 
 * @author student
 *
 */
@SpringBootApplication
@EnableBinding(value={ConsumerChannels.class, ProducerChannels.class})
public class HospitalAppApiGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(HospitalAppApiGatewayApplication.class, args);
	}
}
