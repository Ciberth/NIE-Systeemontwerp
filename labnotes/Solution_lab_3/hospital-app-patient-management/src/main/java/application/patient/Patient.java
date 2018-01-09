package application.patient;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


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
	/*
	public Patient(String socialSecurityNumber, Ward ward) { 
		this.socialSecurityNumber = socialSecurityNumber;
		this.ward = ward;
	}*/

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String socialSecurityNumber) {
		this.ssn = socialSecurityNumber;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	
	
}
