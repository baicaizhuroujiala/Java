package edu.stevens.CS548.clinic.domain;

import edu.stevens.CS548.clinic.domain.Treatment;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: DrugTreatment
 *
 */
@Entity
@DiscriminatorValue("D")			//D specify in the database the D correspond with drug treatment, in TType column D means drug treatment
public class DrugTreatment extends Treatment implements Serializable {

	
	private static final long serialVersionUID = 1L;

	
	private String drug;
	private float dosage;
	
	public String getDrug() {
		return drug;
	}
	public void setDrug(String drug) {
		this.drug = drug;
	}
	public float getDosage() {
		return dosage;
	}
	public void setDosage(float dosage) {
		this.dosage = dosage;
	}
	public void visit(ITreatmentVisitor visitor) {
		visitor.visitDrugTreatment(this.getId(), this.getProviderId(), this.getDiagnosis(), this.drug, this.dosage);
	}
	public DrugTreatment() {
		super();
		this.setTreatmentType("D");			//set treatment type
	}
   
}
