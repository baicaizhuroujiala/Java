package edu.stevens.cs548.clinic.service.web.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

import edu.stevens.cs548.clinic.service.dto.ProviderDTO;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceRemote;

@WebService(
		endpointInterface="edu.stevens.cs548.clinic.service.web.soap.IProviderWebService",
		targetNamespace="http://cs548.stevens.edu/clinic/service/web/soap",
		serviceName="ProviderWebService",
		portName="ProviderWebPort")

public class ProviderWebService implements IProviderWebService {

	IProviderServiceRemote providerService;
	
	@Override
	@WebMethod(operationName = "create")
	public long createProvider(String name, long providerId,
			String specialization) throws ProviderServiceExn {
		return providerService.createProvider(name, providerId, specialization);
	}

	@Override
	@WebMethod
	public ProviderDTO getProviderByProviderId(long providerId)
			throws ProviderServiceExn {
		return providerService.getProviderByProviderId(providerId);
	}

	@Override
	@WebMethod
	public ProviderDTO[] getProviderByName(String name)
			throws ProviderServiceExn {
		return providerService.getProviderByName(name);
	}

	@Override
	@WebMethod
	public TreatmentDto[] getTreatments(long providerId, long[] tids)
			throws ProviderServiceExn {
		return providerService.getTreatments(providerId, tids);
	}

	@Override
	@WebMethod
	public long[] getTreatmentIdsByProviderId(long providerId)
			throws ProviderServiceExn {
		return providerService.getTreatmentIdsByProviderId(providerId);
	}

	@Override
	@WebMethod
	public TreatmentDto[] getTreatmentsOfPatint(long providerId, long patientId)
			throws ProviderServiceExn, PatientServiceExn {
		return providerService.getTreatmentsOfPatint(providerId, patientId);
	}

//	@Override
//	@WebMethod
//	public String siteInfo() {
//		// TODO Auto-generated method stub
//		return providerService.siteInfo();
//	}

}
