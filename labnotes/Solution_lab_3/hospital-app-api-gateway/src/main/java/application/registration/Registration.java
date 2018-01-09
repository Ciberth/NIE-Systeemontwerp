package application.registration;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Registration {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String patient;
	
	@Enumerated(EnumType.STRING)
	private InsuranceType insuranceType;
	
	
	@Enumerated(EnumType.STRING)
	private RoomType roomTypePreference;
	
	
	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public RoomType getRoomTypePreference() {
		return roomTypePreference;
	}

	public void setRoomTypePreference(RoomType roomTypePreference) {
		this.roomTypePreference = roomTypePreference;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
