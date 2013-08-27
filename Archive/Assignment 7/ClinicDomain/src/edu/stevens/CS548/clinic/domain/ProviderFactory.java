package edu.stevens.CS548.clinic.domain;

public class ProviderFactory implements IProviderFactory {

	@Override
	public Provider createProvider(long providerId, String name,
			String specialization) {
		Provider provider = new Provider();
		provider.setProviderId(providerId);
		provider.setName(name);
		provider.setSpecialization(specialization);
		return provider;
	}

}
