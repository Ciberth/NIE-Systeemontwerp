package be.ugent.intec.domainmodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class HospitalStay {

	@Id
	@GeneratedValue
	long id;

	@Override
	public String toString() {
		return "HospitalStay " + id;
	}
	
	
}
