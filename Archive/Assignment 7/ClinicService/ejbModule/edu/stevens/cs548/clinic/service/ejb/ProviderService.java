package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.CS548.clinic.domain.IProviderDAO;
import edu.stevens.CS548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.CS548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.CS548.clinic.domain.ITreatmentVisitor;
import edu.stevens.CS548.clinic.domain.Provider;
import edu.stevens.CS548.clinic.domain.ProviderDAO;
import edu.stevens.CS548.clinic.domain.ProviderFactory;
import edu.stevens.CS548.clinic.domain.RadiologyDate;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.PatientDTO;
import edu.stevens.cs548.clinic.service.dto.ProviderDTO;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;

/**
 * Session Bean implementation class ProviderService
 */
@Stateless(name="ProviderServiceBean")
public class ProviderService implements IProviderServiceLocal, IProviderServiceRemote {

	private ProviderFactory providerFactory;
	private IProviderDAO providerDAO;
    /**
     * Default constructor. 
     */
    public ProviderService() {
    	providerFactory = new ProviderFactory();
    	//providerDAO = new ProviderDAO(em);
    }

    //dependency injection
  	@PersistenceContext(unitName="ClinicDomain")	//name is in persistence.xml
  	private EntityManager em;
  	
  	//called after injection get to be done
  	@PostConstruct	
      private void initialize(){
      	providerDAO = new ProviderDAO(em); 
      }
  	
	/**
	 * @see IProviderService#createProvider(String, long)
     */
    public long createProvider(String name, long providerId, String specialization) throws ProviderServiceExn {
    	Provider provider = this.providerFactory.createProvider(providerId, name, specialization);
    	try {
        	providerDAO.addProvider(provider);
    	} catch (ProviderExn e){
    		throw new ProviderServiceExn(e.toString());
    	}
    	return provider.getId();
    }

    public long createProvider(String name, long providerId) throws ProviderServiceExn {
    	Provider provider = this.providerFactory.createProvider(providerId, name, null);
    	try {
        	providerDAO.addProvider(provider);
    	} catch (ProviderExn e){
    		throw new ProviderServiceExn(e.toString());
    	}
    	return provider.getId();
    }
    
	/**
     * @see IProviderService#getProviderByProviderId(long)
     */
    public ProviderDTO getProviderByProviderId(long providerId) throws ProviderServiceExn{
    	try {
    		Provider provider = providerDAO.getProviderByProviderId(providerId);
    		return new ProviderDTO(provider);
    	} catch (ProviderExn e){
    		throw new ProviderServiceExn(e.toString());
    	}
    }

    /**
     * @see IProviderService#getProviderByName(String)
     */
    public ProviderDTO[] getProviderByName(String name) {
    	List<Provider> providers;
    	providers = providerDAO.getProviderByName(name);
		ProviderDTO[] dto=new ProviderDTO[providers.size()];
    	for(int i=0; i<providers.size(); i++){
    		dto[i]=new ProviderDTO(providers.get(i));
    	}
    	return dto;		
    }
    
    static class TreatmentPDOtoDTO implements ITreatmentVisitor{
		private TreatmentDto dto;
		public TreatmentDto getDTO(){
			return dto;
		}
		
		@Override
		public void visitDrugTreatment(long tid, long pid, String diagnosis, String drug,
				float dosage) {
			dto = new TreatmentDto();
			dto.setTreatmentId(tid);
			dto.setProviderId(pid);
			dto.setDiagnosis(diagnosis);
			DrugTreatmentType drugInfo = new DrugTreatmentType();
			drugInfo.setDosage(dosage);
			drugInfo.setName(drug);
			dto.setDrugTreatment(drugInfo);
		}

		@Override
		public void visitRadiology(long tid, long pid, String diagnosis,
				List<RadiologyDate> dates) {
			dto = new TreatmentDto();
			dto.setTreatmentId(tid);
			dto.setProviderId(pid);
			dto.setDiagnosis(diagnosis);
		}

		@Override
		public void visitSurgery(long tid, long pid, String diagnosis, Date date) {
			dto = new TreatmentDto();
			dto.setTreatmentId(tid);
			dto.setProviderId(pid);
			dto.setDiagnosis(diagnosis);
			SurgeryType surgeryInfo=new SurgeryType();
			surgeryInfo.setDate(date);
		}
		
	}
    /**
     * @see IProviderService#getTreatments(long[])
     */
    public TreatmentDto[] getTreatments(long providerId, long[] tids) throws ProviderServiceExn {
    	try {
			Provider provider = providerDAO.getProviderByProviderId(providerId);
			TreatmentDto[] treatments = new TreatmentDto[tids.length];
			TreatmentPDOtoDTO visitor = new TreatmentPDOtoDTO();
			for (int i=0; i<tids.length; i++){
				provider.visitTreatment(tids[i], visitor);
				treatments[i] = visitor.getDTO();
			}
			return treatments;
		} catch (ProviderExn e){
			throw new ProviderServiceExn(e.toString());
		} catch (TreatmentExn e){
			throw new ProviderServiceExn(e.toString());
		}
    }


	/**
     * @throws ProviderServiceExn 
	 * @see IProviderService#getTreatmentIdsByProviderId(long)
     */
    public long[] getTreatmentIdsByProviderId(long providerId) throws ProviderServiceExn {
    	try {
			Provider provider = providerDAO.getProviderByProviderId(providerId);
			List<Long> treatmentIds = provider.getTreatmentIds();
			long[] treatments = new long[treatmentIds.size()];
			for (int i=0; i<treatmentIds.size(); i++){
				treatments[i] = treatmentIds.get(i);
			}
			return treatments;
		} catch (ProviderExn e){
			throw new ProviderServiceExn(e.toString());
		}
    }

	@Override
	public TreatmentDto[] getTreatmentsOfPatint(long providerId, long patientId)
			throws ProviderServiceExn, PatientServiceExn {
		IPatientService patientService = new PatientService();
		PatientDTO patientDTO=patientService.getPatientByPatientId(patientId);
		long[] treatmentIds=patientDTO.treatments;
		return this.getTreatments(providerId, treatmentIds);
	}

/*	//@Resource(name="SiteInfoOfProvider")
	private String siteInfomation="provider";
	
	@Override
	public String siteInfo() {
		return siteInfomation;
	}*/

}
