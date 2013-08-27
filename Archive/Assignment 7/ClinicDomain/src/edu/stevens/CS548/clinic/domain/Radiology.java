package edu.stevens.CS548.clinic.domain;

import static javax.persistence.CascadeType.REMOVE;
import edu.stevens.CS548.clinic.domain.Treatment;
import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Radiology
 *
 */
@Entity
@DiscriminatorValue("R")			

public class Radiology extends Treatment implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade = REMOVE, mappedBy = "radiology")		
	@OrderBy
	private List<RadiologyDate> dates;

	public List<RadiologyDate> getDates() {
		return dates;
	}

	public void setDates(List<RadiologyDate> dates) {
		this.dates = dates;
	}

	public void visit(ITreatmentVisitor visitor) {
		visitor.visitRadiology(this.getId(), this.getProviderId(), this.getDiagnosis(), this.dates);
	}

	@Transient
	private IRadiologyDateDAO radiologyDateDAO;

	public void setRadiologyDateDAO(IRadiologyDateDAO radiologyDateDAO) {
		this.radiologyDateDAO = radiologyDateDAO;
	}

	public Radiology() {
		super();
		this.setTreatmentType("R");
	}
   
}
