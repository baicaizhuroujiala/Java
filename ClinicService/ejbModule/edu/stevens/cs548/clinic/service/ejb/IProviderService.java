package edu.stevens.cs548.clinic.service.ejb;

import edu.stevens.cs548.clinic.service.dto.ProviderDTO;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;


public interface IProviderService {
	
	public class ProviderServiceExn extends Exception{
		private static final long serialVersionUID = 1L;

		public ProviderServiceExn (String m){
			super(m);
		}
	}
	public long createProvider(String name, long providerId, String specialization) throws ProviderServiceExn;
	public long createProvider(String name, long providerId) throws ProviderServiceExn;
	public ProviderDTO getProviderByProviderId(long providerId) throws ProviderServiceExn;
	public ProviderDTO[] getProviderByName(String name) throws ProviderServiceExn;
	public TreatmentDto[] getTreatments(long providerId, long[] tids) throws ProviderServiceExn;
	public long[] getTreatmentIdsByProviderId(long providerId) throws ProviderServiceExn;
	public TreatmentDto[] getTreatmentsOfPatient(long providerId, long patientId) throws ProviderServiceExn, PatientServiceExn;
//	public String siteInfo();
}
