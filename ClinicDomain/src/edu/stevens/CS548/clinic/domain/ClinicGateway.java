/*
 * An EntityManager instance is associated with a persistence context. 
 * A persistence context is a set of entity instances in which for any persistent entity identity there is a unique entity instance. 
 * Within the persistence context, the entity instances and their lifecycle are managed. 
 * This interface defines the methods that are used to interact with the persistence context. 
 * The EntityManager API is used to create and remove persistent entity instances, to find entities by their primary key, and to query over entities.
 * The set of entities that can be managed by a given EntityManager instance is defined by a persistence unit. 
 * A persistence unit defines the set of all classes that are related or grouped by the application, and which must be colocated in their mapping to a single database.
 * */
// Each thread should has it own entity manager 

package edu.stevens.CS548.clinic.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClinicGateway implements IClinicGateway {

	private EntityManagerFactory emf;		//create entity manager

	@Override
	public IPatientFactory getPatientFactory() {	//
		return new PatientFactory();
	}
	 
	@Override
	public IPatientDAO getPatientDAO() {
		EntityManager em = emf.createEntityManager();
		return new PatientDAO(em);
	}
	
	@Override
	public IProviderFactory getProviderFactory() {
		return new ProviderFactory();
	}

	@Override
	public IProviderDAO getProviderDAO() {
		EntityManager em = emf.createEntityManager();
		return new ProviderDAO(em);
	}
	public ClinicGateway() {
		emf = Persistence.createEntityManagerFactory("ClinicDomain");	//create entity manager factory, name in persistence.xml
	}

	
	
	

	

}
