package edu.stevens.CS548.clinic.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


public class ProviderDAO implements IProviderDAO {

	private EntityManager em;
	private TreatmentDAO treatmentDAO;
	
	

	@Override
	public Provider getProviderByDbId(long id) throws ProviderExn {
		Provider pr = em.find(Provider.class, id);
		if (pr == null){
			throw new ProviderExn("Provider not found: primary id = " + id);
		}
		else {
			pr.setTreatmentDAO(treatmentDAO);
			return pr;
		}
	}
	
	@Override
	public Provider getProviderByProviderId(long providerId) throws ProviderExn {
		TypedQuery<Provider> query = em.createNamedQuery("SearchProviderByProviderID", Provider.class)
									.setParameter("providerId", providerId);
		List<Provider> providers = query.getResultList();
		if (providers.size() > 1)
			throw new ProviderExn("Duplicate provider records: provider id = " + providerId);
		else if (providers.size() < 1){
			return null;
			//throw new ProviderExn("Provider not found: provider id = " + pid);
		}
		else {
			Provider p = providers.get(0);
			p.setTreatmentDAO(this.treatmentDAO);
			return p;
		}
	}
	
	@Override
	public List<Provider> getProviderByName(String name) {
		TypedQuery<Provider> query = em.createNamedQuery("SearchProviderByName", Provider.class)
				.setParameter("name", name);
		List<Provider> providers = query.getResultList();
		for (Provider p : providers){			//for loop of each provider
			p.setTreatmentDAO(this.treatmentDAO);
		}
		return providers;
	}

	@Override
	public void addProvider(Provider provider) throws ProviderExn {
		long providerId = provider.getProviderId();
		TypedQuery<Provider> query = em.createNamedQuery("SearchProviderByProviderID", Provider.class)
									.setParameter("providerId", providerId);
		List<Provider> providers = query.getResultList();
		if (providers.size() < 1){
			em.persist(provider);
			provider.setTreatmentDAO(this.treatmentDAO);
		}
		else {
			Provider provider2 = providers.get(0);
			throw new ProviderExn("Insertion: Provider with provider id (" + providerId + ") already exists.\n** Name: " + provider2.getName());
		} 	
	}
	
	public void addProvider(long providerId, String name) throws ProviderExn {
		Provider provider = new Provider();
		provider.setProviderId(providerId);
		provider.setName(name);
		this.addProvider(provider);
	}

	@Override
	public void deleteProvider(Provider provider) throws ProviderExn {
		em.remove(provider);
	}
	
	public ProviderDAO(EntityManager em){
		this.em = em;
		this.treatmentDAO = new TreatmentDAO(em);
	}

}
