package application.patient;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;



public interface PatientRepository extends CrudRepository<Patient, String> {

	@Query("SELECT p FROM Patient p WHERE (p.firstname LIKE 'J%')")
	List<Patient> getPaitentsStartingWithJ();
}
