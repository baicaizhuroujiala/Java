package edu.stevens.CS548.clinic.domain;

public interface IProviderFactory {
	public Provider createProvider (long providerId, String name, String specialization);
}
