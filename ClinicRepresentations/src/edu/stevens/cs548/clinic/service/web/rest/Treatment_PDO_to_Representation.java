package edu.stevens.cs548.clinic.service.web.rest;

import java.util.Date;
import java.util.List;

import edu.stevens.CS548.clinic.domain.ITreatmentVisitor;
import edu.stevens.CS548.clinic.domain.RadiologyDate;

public class Treatment_PDO_to_Representation implements ITreatmentVisitor {
	
	private TreatmentRepresentation rep;
	
	public TreatmentRepresentation getRep(){
		return rep;
	}

	@Override
	public void visitDrugTreatment(long tid, String diagnosis, String drug, float dosage) {
		rep.setId(tid);
		rep.setDiagnosis(diagnosis);
		DrugTreatmentRepresentation drugTreatment = new DrugTreatmentRepresentation(drug, dosage);
		rep.setDrugTreatment(drugTreatment);
	}

	@Override
	public void visitRadiology(long tid, String diagnosis, List<RadiologyDate> dates) {
		rep.setId(tid);
		rep.setDiagnosis(diagnosis);
		RadiologyRepresentation radiology = new RadiologyRepresentation(dates);
		rep.setRadiology(radiology);
	}

	@Override
	public void visitSurgery(long tid, String diagnosis, Date date) {
		rep.setId(tid);
		rep.setDiagnosis(diagnosis);
		SurgeryRepresentation surgery = new SurgeryRepresentation(date);
		rep.setSurgery(surgery);
	}

}
