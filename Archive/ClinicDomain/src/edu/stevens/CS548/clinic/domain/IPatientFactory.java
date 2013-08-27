//create patient record
package edu.stevens.CS548.clinic.domain;

import java.util.Date;

public interface IPatientFactory {
	public Patient createPatient (long pid, String name, Date dob);
}
