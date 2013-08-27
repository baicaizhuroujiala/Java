package edu.stevens.cs548.clinic.service.web.soap;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;

import edu.stevens.CS548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.service.dto.PatientDTO;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.AddingTreatmentExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.TreatmentNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceRemote;

@WebService(
		endpointInterface="edu.stevens.cs548.clinic.service.web.soap.IPatientWebService",
		targetNamespace="http://cs548.stevens.edu/clinic/service/web/soap",
		serviceName="PatientWebService",
		portName="PatientWebPort")


public class PatientWebService implements IPatientWebService
{	
	@EJB(beanName="PatientServiceBean")
	IPatientServiceRemote service;
	
	@Override
	@WebMethod
	public long createPatient(String name, int age, Date dob, long patientId)
			throws PatientServiceExn {
		return service.createPatient(name, age, dob, patientId);
	}

	@Override
	@WebMethod
	public PatientDTO getPatientByDbId(long id) throws PatientServiceExn {
		return service.getPatientByDbId(id);
	}

	@Override
	@WebMethod
	public PatientDTO getPatientByPatientId(long pid) throws PatientServiceExn {
		return service.getPatientByPatientId(pid);
	}

	@Override
	@WebMethod
	public PatientDTO[] getPatientByNameDob(String name, Date dob) {
		return service.getPatientByNameDob(name, dob);
	}

	@Override
	@WebMethod
	public void deletePatient(String name, long id) throws PatientServiceExn {
		this.service.deletePatient(name, id);
	}

	@Override
	@WebMethod
	public void addDrugTreatment(long tid, long pid, String diagnosis, String drug,
			float dosage) throws PatientNotFoundExn, AddingTreatmentExn{
		this.service.addDrugTreatment(tid, pid, diagnosis, drug, dosage);
	}

	@Override
	@WebMethod
	public void addSurgery(long id, String diagnosis, Date date, long providerId)
			throws PatientNotFoundExn, AddingTreatmentExn{
		this.service.addSurgery(id, diagnosis, date, providerId);
	}

	@Override
	@WebMethod
	public void addRadiology(long id, String diagnosis, List<Date> dates,
			long providerId) throws PatientNotFoundExn, AddingTreatmentExn {
		this.service.addRadiology(id, diagnosis, dates, providerId);
	}
	
	@Override
	@WebMethod
	public TreatmentDto[] getTreatments(long id, long[] tids)
			throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn {
		return service.getTreatments(id, tids);
	}

	@Override
	@WebMethod
	public void deleteTreatment(long id, long tid) throws PatientNotFoundExn,
			TreatmentNotFoundExn, PatientServiceExn {
		this.service.deleteTreatment(id, tid);
	}

	@Override
	@WebMethod
	public String siteInfo() {
		return service.siteInfo();
	}

}
