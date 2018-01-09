package be.ugent.intec.domainmodel.invoice;

import java.text.MessageFormat;

public class PatientInfo {
	
	private String ssn;
	private String name;
	
	public PatientInfo(String ssn, String name) {
		this.ssn = ssn;
		this.name = name;
	}

	public String getSsn() {
		return this.ssn;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return MessageFormat.format("({0}) {1}", this.ssn, this.name);
	}


}
