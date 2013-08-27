package edu.stevens.CS548.clinic.domain;

import javax.persistence.EntityManager;

import edu.stevens.CS548.clinic.domain.IProviderDAO.ProviderExn;

public class TreatmentDAO implements ITreatmentDAO {

	public TreatmentDAO(EntityManager em){
		this.em = em;
	}
	private EntityManager em;
	
	@Override
	public Treatment getTreatmentByDbId(long id) throws TreatmentExn {
		Treatment t = em.find(Treatment.class, id);
		if (t == null) {
			throw new TreatmentExn("Missing treatment: id = " + id);
		}
		else {
			return t;
		}
	}

	@Override
	public void addTreatment(Treatment t, long providerId) throws TreatmentExn {
		IProviderDAO providerDAO = new ProviderDAO(em);
		try {
			providerDAO.getProviderByProviderId(providerId).addTreatment(t);
		} catch (ProviderExn e) {
			throw new TreatmentExn("Adding Treatment to provider");
		}
		em.persist(t); 
	}
	
	@Override
	public void deleteTreatment(Treatment t) {
		em.remove(t);
	}

}
