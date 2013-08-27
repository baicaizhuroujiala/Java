//operations on patient, mediating access to database.

package edu.stevens.CS548.clinic.domain;

import java.util.Date;
import java.util.List;

public interface IPatientDAO {
	public static class PatientExn extends Exception {
		private static final long serialVersionUID = 1L;
		public PatientExn(String msg){			//Constructor of PatientExn
			super(msg);
		}	
	}
	
	public Patient getPatientByPatientId (long pid) throws PatientExn;
	public Patient getPatientByDbId(long id) throws PatientExn;
	public List<Patient> getPatientByNameDob(String name,Date dob);
	
	//using factory to create a patient object, the object will not be connect to the manage pool, until called addPatient() 
	public void addPatient (Patient pat) throws PatientExn;
	public void deletePatient (Patient pat) throws PatientExn;
}
