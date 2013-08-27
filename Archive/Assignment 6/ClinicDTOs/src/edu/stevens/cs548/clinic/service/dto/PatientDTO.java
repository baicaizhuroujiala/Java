package edu.stevens.cs548.clinic.service.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.stevens.CS548.clinic.domain.Patient;


//Custom a namespace
@XmlRootElement(name="patient-dto", namespace="http://www.example.org/clinic/schemas/patient")
public class PatientDTO {
	public long id;
	@XmlElement(name="patient-id")
	public long patientId;
	@XmlElement(required=true)
	public String name;
	@XmlElement(required=true, name="dob")
	public Date brithDate;
	public long[] treatments;  
	
    public PatientDTO() { }
	
	public PatientDTO(Patient patient) {
		this.id=patient.getId();
		this.patientId=patient.getPatientId();
		this.name=patient.getName();
		this.brithDate=patient.getBirthDate();
		List<Long> tids=patient.getTreatmentIds();
		this.treatments=new long[tids.size()];
		for(int i=0; i<this.treatments.length; i++){
			this.treatments[i]=tids.get(i);
		}
	}
}

