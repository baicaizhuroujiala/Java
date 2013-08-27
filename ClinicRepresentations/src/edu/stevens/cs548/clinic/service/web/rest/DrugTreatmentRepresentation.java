package edu.stevens.cs548.clinic.service.web.rest;

import edu.stevens.cs548.clinic.service.web.rest.data.DrugTreatmentType;

public class DrugTreatmentRepresentation extends DrugTreatmentType{

	public DrugTreatmentRepresentation(String drug, float dosage) {
		this.name=drug;
		this.dosage=dosage;
	}

}
