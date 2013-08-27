package edu.stevens.CS548.clinic.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class PatientDAO implements IPatientDAO {
	
	//only can do dependence injection on services. 
	private EntityManager em;
	private TreatmentDAO treatmentDAO;
	
	@Override
	public Patient getPatientByDbId(long id) throws PatientExn {
		//first argument is the type of the result, second argument is primary key
		Patient p = em.find(Patient.class, id);		
		if (p == null){
			throw new PatientExn("Patient not found: primary key = " + id);
		}
		else {
			p.setTreatmentDAO(this.treatmentDAO);
			return p;
		}
	}

	@Override
	public Patient getPatientByPatientId(long pid) throws PatientExn {
		TypedQuery<Patient> query = em.createNamedQuery("SearchPatientByPatientID", Patient.class)
									.setParameter("pid",pid);
		List<Patient> patients = query.getResultList();
		if (patients.size() > 1)
			throw new PatientExn("Duplicate patient records: patient id = " + pid);
		else if (patients.size() < 1){
			return null;
			//throw new PatientExn("Patient not found: patient id = " + pid);
		}
		else {
			Patient p = patients.get(0);
			p.setTreatmentDAO(this.treatmentDAO);
			return p;
		}
			
	}

	@Override
	public List<Patient> getPatientByNameDob(String name, Date dob) {
		//store the query to query variable. Type is TypedQuery, and inside the <> is return type.
		//in Patient.java @NamedQuery, :name and :dob are parameter, so need to invoke setParameter method.
		TypedQuery<Patient> query = em.createNamedQuery("SearchPatientByNameDOB", Patient.class)
									.setParameter("name",name)
									.setParameter("dob", dob);
		List<Patient> patients = query.getResultList();
		for (Patient p : patients){			//for loop of each patient
			p.setTreatmentDAO(this.treatmentDAO);
		}
		return patients;
	}

	@Override
	//persist a patient object to database
	public void addPatient(Patient patient) throws PatientExn{
		long pid = patient.getPatientId();
		TypedQuery<Patient> query = em.createNamedQuery("SearchPatientByPatientID", Patient.class)
				.setParameter("pid",pid);
		List<Patient> patients = query.getResultList();
		if (patients.size() < 1){
			em.persist(patient);
			patient.setTreatmentDAO(this.treatmentDAO);
		}
		else {
			Patient patient2 = patients.get(0);
			throw new PatientExn("Insertion: Patient with patient id (" + pid + ") already exists.\n** Name: " + patient2.getName());
		} 
	}

	@SuppressWarnings("deprecation")
	//Do not have a patientId
	public void addPatient(String name, Date dob, int age) throws PatientExn{
		Patient patient = new Patient();
		patient.setName(name);
		patient.setBirthDate(dob);
		Date nowDate = new Date();
		//calculate age, system do not recommend to use getYear(), getMonth(), getDate()
		int tempAge = nowDate.getYear()-dob.getYear();
		if (nowDate.getMonth()<dob.getMonth()){
			tempAge --;
		}
		else if (nowDate.getMonth()==dob.getMonth() && nowDate.getDate()<dob.getDate()){
			tempAge --;
		}
		if (tempAge == age){
			em.persist(patient);
			patient.setTreatmentDAO(this.treatmentDAO);
		}
		else {
			throw new PatientExn("Day of birth and age do not match");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void addPatient(String name, Date dob, int age, long pid) throws PatientExn{
		Patient patient = new Patient();
		patient.setName(name);
		patient.setBirthDate(dob);
		patient.setPatientId(pid);
		Date nowDate = new Date();
		int tempAge = nowDate.getYear()-dob.getYear();
		if (nowDate.getMonth()<dob.getMonth()){
			tempAge --;
		}
		else if (nowDate.getMonth()==dob.getMonth() && nowDate.getDate()<dob.getDate()){
			tempAge --;
		}
		if (tempAge == age){
			TypedQuery<Patient> query = em.createNamedQuery("SearchPatientByPatientID", Patient.class)
					.setParameter("pid",pid);
			List<Patient> patients = query.getResultList();
			if (patients.size() < 1){
				em.persist(patient);
				patient.setTreatmentDAO(this.treatmentDAO);
			}
			else {
				Patient patient2 = patients.get(0);
				throw new PatientExn("Insertion: Patient with patient id (" + pid + ") already exists.\n** Name: " + patient2.getName());
			}
		}
		else {
			throw new PatientExn("Day of birth and age do not match");
		}
	}
	@Override
	public void deletePatient(Patient patient) throws PatientExn {
//		em.createQuery("delete from Treatment t where t.patient.id = :id")
//			.setParameter("id", patient.getId())
//			.executeUpdate();		//execute query
		em.remove(patient);
	}
	
	public void deletePatient(long pid) throws PatientExn {
		em.remove(this.getPatientByPatientId(pid));
	}
	//get EntityManager from ClinicGateway  and create a treatmentDAO
	public PatientDAO(EntityManager em){
		this.em = em;
		this.treatmentDAO = new TreatmentDAO(em);
	}

}
