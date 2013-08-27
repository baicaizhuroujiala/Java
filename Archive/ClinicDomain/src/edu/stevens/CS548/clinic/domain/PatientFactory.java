//implement the IPatientFactory interface
//every time JPA get patient records from database, it will create patient objects and  initialize it.
package edu.stevens.CS548.clinic.domain;

import java.util.Date;

public class PatientFactory implements IPatientFactory {

	@Override
	public Patient createPatient(long pid, String name, Date dob) {
		Patient p = new Patient();
		p.setPatientId(pid);
		p.setName(name);
		p.setBirthDate(dob);
		return p;
	}
}
