package edu.stevens.CS548.clinic.domain;

import javax.persistence.EntityManager;

public class RadiologyDateDAO implements IRadiologyDateDAO {
	
	private EntityManager em;
	
	public RadiologyDateDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	public RadiologyDate getRadiologyDateByDbId(long id) throws RadiologyDateExn {
		RadiologyDate rd = em.find(RadiologyDate.class, id);
		if (rd == null){
			throw new RadiologyDateExn("Missing RadiologyDate: id = " + id);
		}
		else {
			return rd;
		}
	}

	@Override
	public void addRadiologyDate(RadiologyDate date) {
		em.persist(date);
	}

	@Override
	public void deleteRadiologyDate(RadiologyDate date) {
		em.remove(date);
	}

}
