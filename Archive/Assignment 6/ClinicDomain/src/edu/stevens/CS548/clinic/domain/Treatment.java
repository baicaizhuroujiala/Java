package edu.stevens.CS548.clinic.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Treatment
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)	//specify how this hierarchy represent in database
@DiscriminatorColumn(name="TTYPE")				//In the database, to discriminate which subclass of treatment. name is the column of database

@Table(name="TREATMENT")

public abstract class Treatment implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	private long providerId;
	private String diagnosis;
	
	@Column(name="TTYPE",length=2)
	private String treatmentType;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getProviderId() {
		return providerId;
	}

	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}

	public String getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	}

	public String getDiagnosis() {
		return diagnosis;
	}
	
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	@ManyToOne
	//name of the patient column in the database is patient_fk, 
	//referencedColumnName means id in the patient is the corresponding foreign key. 
	@JoinColumn(name = "patient_fk", referencedColumnName = "id")
	private Patient patient;
	
	public Patient getPatient() {
		return patient;
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
		if (!patient.getTreatments().contains(this))
			patient.addTreatment(this);
	}
	
	@ManyToOne
	@JoinColumn(name = "provider_fk", referencedColumnName = "id")
	private Provider provider;
	
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	//according different type of treatments to invoke different visit methods.
	public abstract void visit(ITreatmentVisitor visitor);
	
	public Treatment() {
		super();
	}
   
}
