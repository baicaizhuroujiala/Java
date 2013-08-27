package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.CS548.clinic.domain.IPatientDAO;
import edu.stevens.CS548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.CS548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.CS548.clinic.domain.ITreatmentVisitor;
import edu.stevens.CS548.clinic.domain.Patient;
import edu.stevens.CS548.clinic.domain.PatientDAO;
import edu.stevens.CS548.clinic.domain.PatientFactory;
import edu.stevens.CS548.clinic.domain.RadiologyDate;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.PatientDTO;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

/**
 * Session Bean implementation class PatientService
 */
@Stateless(name="PatientServiceBean")
public class PatientService implements IPatientServiceLocal, IPatientServiceRemote {

	private PatientFactory patientFactory;
	private IPatientDAO patientDAO;
	
	/**
     * Default constructor. 
     */
    public PatientService() {
        patientFactory = new PatientFactory();
        //patientDAO = new PatientDAO(em);
    }
	
	//dependency injection
	@PersistenceContext(unitName="ClinicDomain")	//name is in persistence.xml
	private EntityManager em;
	
	//called after injection get to be done
	@PostConstruct	
    private void initialize(){
    	patientDAO = new PatientDAO(em); 
    }


	/**
     * @see IPatientService#getPatientByDbId(long)
     */
    public PatientDTO getPatientByDbId(long id) throws PatientServiceExn {
    	try {
    		Patient patient = patientDAO.getPatientByDbId(id);
    		return new PatientDTO(patient);
    	} catch (PatientExn e){
    		throw new PatientServiceExn(e.toString());
    	}
    }
	
	/**
     * @see IPatientService#getPatientByNameDob(String, Date)
     */
    public PatientDTO[] getPatientByNameDob(String name, Date dob) {
    	List<Patient> patients=patientDAO.getPatientByNameDob(name,dob);
    	PatientDTO[] dto=new PatientDTO[patients.size()];
    	for(int i=0; i<patients.size(); i++){
    		dto[i]=new PatientDTO(patients.get(i));
    	}
    	return dto;
    }

	/**
     * @see IPatientService#getPatientByPatientId(long)
     */
    public PatientDTO getPatientByPatientId(long pid) throws PatientServiceExn{
    	try {
    		Patient patient = patientDAO.getPatientByPatientId(pid);
    		return new PatientDTO(patient);
    	} catch (PatientExn e){
    		throw new PatientServiceExn(e.toString());
    	}			
    }

	/**
     * @see IPatientService#createPatient(String, Date, long)
     */
    public long createPatient(String name,int age, Date dob, long patientId) throws PatientServiceExn {
    	Patient patient = this.patientFactory.createPatient(patientId, name, dob, age);
    	try {
        	patientDAO.addPatient(patient);
    	} catch (PatientExn e){
    		throw new PatientServiceExn(e.toString());
    	}
    	
    	return patient.getId();
    }

	/**
     * @see IPatientService#deletePatient(String, long)
     */
    public void deletePatient(String name, long id) throws PatientServiceExn {
    	try {
    		Patient patient=patientDAO.getPatientByDbId(id);
    		if(!name.equals(patient.getName())){
    			throw new PatientServiceExn("Tried to delete wrong patient: " +
    					"						name = " + name + " , id = " + id);
    		}
    		else {
    			patientDAO.deletePatient(patient);
    		}
    	} catch (PatientExn e){
    		throw new PatientServiceExn(e.toString()); 
    	}
    }


	@Override
		public long addDrugTreatment(long id, long providerId, String diagnosis, String drug,
			float dosage) throws PatientNotFoundExn, AddingTreatmentExn {
		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			return patient.addDrugTreatment(diagnosis, drug, dosage, providerId);
		} catch(PatientExn e){
			throw new PatientNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new AddingTreatmentExn(e.toString());
		}
		
	}

	@Override
	public long addSurgery(long id, String diagnosis, Date date, long providerId) throws PatientNotFoundExn, AddingTreatmentExn {
		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			return patient.addSurgery(diagnosis, date, providerId);
		} catch(PatientExn e){
			throw new PatientNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new AddingTreatmentExn(e.toString());
		}
	}


	@Override
	public long addRadiology(long id, String diagnosis, List<Date> dates, long providerId) throws PatientNotFoundExn, AddingTreatmentExn {
		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			return patient.addRadiology(diagnosis, dates, providerId);
		} catch(PatientExn e){
			throw new PatientNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new AddingTreatmentExn(e.toString());
		}
	}

	static class TreatmentPDOtoDTO implements ITreatmentVisitor{
		private TreatmentDto dto;
		public TreatmentDto getDTO(){
			return dto;
		}
		
		@Override
		public void visitDrugTreatment(long tid, String diagnosis, String drug,
				float dosage) {
			dto = new TreatmentDto();
			dto.setTreatmentId(tid);
//			dto.setProviderId(pid);
			dto.setDiagnosis(diagnosis);
			DrugTreatmentType drugInfo = new DrugTreatmentType();
			drugInfo.setDosage(dosage);
			drugInfo.setName(drug);
			dto.setDrugTreatment(drugInfo);
		}

		@Override
		public void visitRadiology(long tid, String diagnosis,
				List<RadiologyDate> dates) {
			dto = new TreatmentDto();
			dto.setTreatmentId(tid);
//			dto.setProviderId(pid);
			dto.setDiagnosis(diagnosis);
		}

		@Override
		public void visitSurgery(long tid, String diagnosis, Date date) {
			dto = new TreatmentDto();
			dto.setTreatmentId(tid);
//			dto.setProviderId(pid);
			dto.setDiagnosis(diagnosis);
			SurgeryType surgeryInfo=new SurgeryType();
			surgeryInfo.setDate(date);
			dto.setSurgery(surgeryInfo);
		}
		
	}
	
	@Override
	public TreatmentDto[] getTreatments(long id, long[] tids)
			throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn {
		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			TreatmentDto[] treatments = new TreatmentDto[tids.length];
			TreatmentPDOtoDTO visitor = new TreatmentPDOtoDTO();
			for (int i=0; i<tids.length; i++){
				patient.visitTreatment(tids[i], visitor);
				treatments[i] = visitor.getDTO();
			}
			return treatments;
		} catch (PatientExn e){
			throw new PatientNotFoundExn(e.toString());
		} catch (TreatmentExn e){
			throw new PatientServiceExn(e.toString());
		}
	}


	@Override
	public void deleteTreatment(long id, long tid) throws PatientNotFoundExn,
			TreatmentNotFoundExn, PatientServiceExn {
		try {
			Patient patient=patientDAO.getPatientByDbId(id);
			patient.deleteTreatment(tid);
		} catch(PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		} catch(TreatmentExn e) {
			throw new PatientServiceExn(e.toString());
		}
	}

	@Resource(name="SiteInfo")
	private String siteInfomation;

	@Override
	public String siteInfo() {
		return siteInfomation;
	}
	
}
