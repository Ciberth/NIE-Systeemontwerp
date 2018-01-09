package application.registration;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * for more details on repositories see: https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
 *
 */
public interface RegistrationRepository  extends CrudRepository<Registration, Long> {
	
}
