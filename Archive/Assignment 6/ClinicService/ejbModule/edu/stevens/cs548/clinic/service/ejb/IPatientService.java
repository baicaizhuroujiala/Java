package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.service.dto.PatientDTO;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

public interface IPatientService {
	
	public class PatientServiceExn extends Exception{
		private static final long serialVersionUID = 1L;
		public PatientServiceExn(String m){
			super(m);
		}
		
	}
	public class PatientNotFoundExn extends PatientServiceExn{
		private static final long serialVersionUID = 1L;

		public PatientNotFoundExn(String m){
			super(m);
		}
	}
	public class TreatmentNotFoundExn extends PatientServiceExn{
		private static final long serialVersionUID = 1L;

		public TreatmentNotFoundExn(String m){
			super(m);
		}
	}
	public long createPatient(String name, int age, Date dob, long patientId) throws PatientServiceExn;
	
	public PatientDTO getPatientByDbId(long id) throws PatientServiceExn;
	public PatientDTO getPatientByPatientId(long pid) throws PatientServiceExn;
	public PatientDTO[] getPatientByNameDob(String name, Date dob);
	public void deletePatient(String name, long id) throws PatientServiceExn;
	public void addDrugTreatment(long id, long providerId, String diagnosis, String drug, float dosage) throws PatientNotFoundExn;	
	public void addSurgery(long id, String diagnosis, Date date, long providerId) throws PatientNotFoundExn;
	public void addRadiology(long id, String diagnosis, List<Date> dates, long providerId) throws PatientNotFoundExn;
	public TreatmentDto[] getTreatments(long id, long[] tids) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn;
	public void deleteTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn;
	public String siteInfo();
}
