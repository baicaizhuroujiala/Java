package edu.stevens.CS548.clinic.domain;

import java.util.List;




public interface IProviderDAO {
	public static class ProviderExn extends Exception {
		private static final long serialVersionUID = 1L;
		public ProviderExn(String msg){			//Constructor of ProviderExn
			super(msg);
		}	
	}
	
	public Provider getProviderByDbId(long id) throws ProviderExn;
	public Provider getProviderByProviderId(long providerId) throws ProviderExn;
	public List<Provider> getProviderByName(String name) throws ProviderExn;

	//using factory to create a Provider object, the object will not be connect to the manage pool, until called addProvider() 
	public void addProvider (Provider provider) throws ProviderExn;
	public void deleteProvider (Provider provider) throws ProviderExn;
}
