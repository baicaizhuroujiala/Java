package edu.stevens.cs548.clinic.service.web.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

import edu.stevens.cs548.clinic.service.dto.ProviderDTO;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;

@WebService(
		name="IProviderWebPort",
		targetNamespace="http://cs548.stevens.edu/clinic/service/web/soap")

public interface IProviderWebService {
	
	@WebMethod(operationName="create")
	public long createProvider(String name, long providerId, String specialization) throws ProviderServiceExn;
	@WebMethod
	public ProviderDTO getProviderByProviderId(long providerId) throws ProviderServiceExn;
	@WebMethod
	public ProviderDTO[] getProviderByName(String name) throws ProviderServiceExn;
	@WebMethod
	public TreatmentDto[] getTreatments(long providerId, long[] tids) throws ProviderServiceExn;
	@WebMethod
	public long[] getTreatmentIdsByProviderId(long providerId) throws ProviderServiceExn;
	@WebMethod
	public TreatmentDto[] getTreatmentsOfPatint(long providerId, long patientId) throws ProviderServiceExn, PatientServiceExn;
//	@WebMethod
//	public String siteInfo();
}
