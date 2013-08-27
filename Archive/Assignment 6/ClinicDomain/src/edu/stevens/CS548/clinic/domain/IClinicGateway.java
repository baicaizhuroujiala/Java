package edu.stevens.CS548.clinic.domain;

public interface IClinicGateway {
	public IPatientFactory getPatientFactory();		//create patient record
	public IPatientDAO getPatientDAO();				//persist patient to database, lookup patient in the database, 
	public IProviderFactory getProviderFactory();		//create provider record
	public IProviderDAO	getProviderDAO();
}
