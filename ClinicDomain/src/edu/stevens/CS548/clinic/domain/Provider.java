package edu.stevens.CS548.clinic.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import edu.stevens.CS548.clinic.domain.ITreatmentDAO.TreatmentExn;

/**
 * Entity implementation class for Entity: Provider
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(
		name="SearchProviderByProviderID", 
		query="select p from Provider p where p.providerId = :providerId"),
	@NamedQuery(
		name="SearchProviderByName", 
		query="select p from Provider p where p.name = :name")
})
@Table(name="PROVIDER")

public class Provider implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	private long providerId;
	private String name;	
	private String specialization;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specializations) {
		this.specialization = specializations;
	}

	@Transient			//means this field do not need to be persisted in the database
	private ITreatmentDAO treatmentDAO;
	
	public void setTreatmentDAO(ITreatmentDAO tdao){
		this.treatmentDAO = tdao;
	}
	
	@OneToMany(mappedBy = "provider")		
	@OrderBy
    private List<Treatment> treatments;
	
	public List<Treatment> getTreatments() {
		return treatments;
	}
	
	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}
	

	protected void addTreatment (Treatment t) {
		this.getTreatments().add(t);		//add t in the list of treatments
		if (t.getProvider() != this)
			t.setProvider(this);
	}
	
	public List<Long> getTreatmentIds(){
		List<Long> tids = new ArrayList<Long>();
		for(Treatment t : this.getTreatments()){
			tids.add(t.getId());
		}
		return tids;
	}
	
	public List<Long> getTreatmentIdsByParient(Patient patient){
		List<Long> tids = new ArrayList<Long>();
		List<Long> ptids = patient.getTreatmentIds();
		for (int i =0; i<ptids.size(); i++){
			for(Treatment t : this.getTreatments()){
				if (ptids.get(i) == t.getId()){
					tids.add(t.getId());
				}
			}
		}
		
		return tids;
	}
	
	public void visitTreatment(long tid, ITreatmentVisitor visitor) throws TreatmentExn {
		Treatment t = treatmentDAO.getTreatmentByDbId(tid);
		if (t.getProvider() != this){
			throw new TreatmentExn("Inappropriate treatment access: provider =" + id + ",treatment =" + tid);
		}
		t.visit(visitor);
	}
	
	public Provider() {
		super();
		treatments = new ArrayList<Treatment>();
	}
   
}
