package edu.stevens.CS548.clinic.domain;

import javax.persistence.EntityManager;

public class RadiologyDAO implements IRadiologyDAO {
	
	private EntityManager em;
	private RadiologyDateDAO radiologyDateDAO;

	@Override
	public Radiology getRadiologyByDbId(long id) throws RadiologyExn {
		Radiology r = em.find(Radiology.class, id);
		if (r == null){
			throw new RadiologyExn("Radiology not found: primary key=" + id);
		}
		else {
			r.setRadiologyDateDAO(this.radiologyDateDAO);
			return r;
		}
	}

	@Override
	public void addRadiology(Radiology radiology) {
		em.persist(radiology);
		radiology.setRadiologyDateDAO(this.radiologyDateDAO);
	}

	@Override
	public void deleteRadiology(Radiology radiology) {
		em.remove(radiology);
	}

	public RadiologyDAO(EntityManager em){
		this.em = em;
		this.radiologyDateDAO = new RadiologyDateDAO(em);
	}
}
