package be.ugent.intec.domainmodel;

import java.text.MessageFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class HospitalBooking {
	
	@Id
	@GeneratedValue
	private long id;
	private String patientSSN;
	//other fields to be added later.
	
	public HospitalBooking(String patientSSN) {
		this.patientSSN = patientSSN;
	}
	
	public HospitalBooking() {
		
	}

	public long getId() {
		return id;
	}


	public String getPatientSSN() {
		return patientSSN;
	}
	
	
	@Override
	public String toString() {
		return MessageFormat.format("({0}): {1}", id, patientSSN);
	}
	
}
