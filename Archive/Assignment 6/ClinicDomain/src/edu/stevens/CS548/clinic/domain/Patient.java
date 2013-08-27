package edu.stevens.CS548.clinic.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.*;

import edu.stevens.CS548.clinic.domain.ITreatmentDAO.TreatmentExn;

import java.util.List;
import static javax.persistence.CascadeType.REMOVE;


/**
 * Entity implementation class for Entity: Patient
 *
 */

@Entity
//write 2 named queries:SearchPatientByNameDOB SearchPatientByPatientID
@NamedQueries({
	@NamedQuery(
		name="SearchPatientByNameDOB", 
		query="select p from Patient p where p.name = :name and p.birthDate = :dob"),
	@NamedQuery(
		name="SearchPatientByPatientID", 
		query="select p from Patient p where p.patientId = :pid")
})
@Table(name="PATIENT")

public class Patient implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	private long patientId;
	private String name;
	@Temporal(TemporalType.DATE)		//JPA will translate java.date to sql.date
	private Date birthDate;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getPatientId() {
		return patientId;
	}
	
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	//a patient field in treatment class will use as foreign key
	//cascade = REMOVE means if a patient record be removed from the database, 
	//the treatments belong to this patient will be deleted too.
	//aggregate pattern: can not access treatment directly, 
	//need to access treatment though patient or provider
	
	//TODO: need to add treatment factory
	@OneToMany(cascade = REMOVE, mappedBy = "patient")		
	@OrderBy
	private List<Treatment> treatments;
	
	//in fact, the set and get methods need to be private, 
	//	but the JPA require them to be public. 
	//	We should not to use it directly.
	public List<Treatment> getTreatments() {
		return treatments;
	}
	
	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}
	
	@Transient			//means this field do not need to be persisted in the database
	private ITreatmentDAO treatmentDAO;
	
	protected void setTreatmentDAO(ITreatmentDAO tdao){
		this.treatmentDAO = tdao;
	}
	
	protected void addTreatment (Treatment t) {
		this.treatmentDAO.addTreatment(t);	//persist treatment object in the database
		this.getTreatments().add(t);		//add t in the list of treatments
		if (t.getPatient() != this)
			t.setPatient(this);
	}
	
	public void addDrugTreatment (String diagnosis, String drug, float dosage, long providerId){
		DrugTreatment drugTreatment = new DrugTreatment();
		drugTreatment.setProviderId(providerId);
		drugTreatment.setDiagnosis(diagnosis);
		drugTreatment.setDrug(drug);
		drugTreatment.setDosage(dosage);
		this.addTreatment(drugTreatment);
	}
	
	public void addSurgery(String diagnosis, Date date, long providerId){
		Surgery surgery = new Surgery();
		surgery.setProviderId(providerId);
		surgery.setDiagnosis(diagnosis);
		surgery.setDate(date);
		this.addTreatment(surgery);
	}
	
	public void addRadiology(String diagnosis, List<Date> dates, long providerId){
		Radiology radiology = new Radiology();
		radiology.setProviderId(providerId);
		radiology.setDiagnosis(diagnosis);
		//change List<Date> to List<RadiologyDate>
		List<RadiologyDate> radiologyDates = new ArrayList<RadiologyDate>();
		for (int i=0; i<dates.size(); i++){
			RadiologyDate tempDate = new RadiologyDate();
			tempDate.setDate(dates.get(i));
			radiologyDates.add(tempDate);
		}
		radiology.setDates(radiologyDates);
		this.addTreatment(radiology);
	}
	
	public List<Long> getTreatmentIds(){
		List<Long> tids = new ArrayList<Long>();
		for(Treatment t : this.getTreatments()){
			tids.add(t.getId());
		}
		return tids;
	}
	
	public void visitTreatment(long tid, ITreatmentVisitor visitor) throws TreatmentExn {
		Treatment t = treatmentDAO.getTreatmentByDbId(tid);
		if (t.getPatient() != this){
			throw new TreatmentExn("Inappropriate treatment access: patient =" + id + ",treatment =" + tid);
		}
		t.visit(visitor);
	}
	
	public void deleteTreatment(long tid) throws TreatmentExn {
		Treatment t = treatmentDAO.getTreatmentByDbId(tid);
		if (t.getPatient() != this){
			throw new TreatmentExn("Inappropriate treatment access: patient =" + id + ",treatment =" + tid);
		}
		treatmentDAO.deleteTreatment(t);
	}
	
	//return all the treatment
	public void visitTreatments(ITreatmentVisitor visitor) {
		for (Treatment t : this.getTreatments()){
			t.visit(visitor);
		}
	}
	
	
	public Patient() {
		super();
		treatments = new ArrayList<Treatment>();
	}
   
}
