package edu.stevens.cs548.clinic.service.web.rest.resources;

import java.net.URI;
import java.util.Date;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.DatatypeConverter;

import edu.stevens.cs548.clinic.service.dto.PatientDTO;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.AddingTreatmentExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.TreatmentNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceRemote;
import edu.stevens.cs548.clinic.service.web.rest.DrugTreatmentRepresentation;
import edu.stevens.cs548.clinic.service.web.rest.PatientRepresentation;
import edu.stevens.cs548.clinic.service.web.rest.TreatmentRepresentation;
import edu.stevens.cs548.clinic.service.web.rest.data.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.web.rest.data.RadiologyType;
import edu.stevens.cs548.clinic.service.web.rest.data.SurgeryType;

@Path("/patient")
public class PatientRescource {
    @Context
    private UriInfo context;

    /**
     * Default constructor. 
     */
    public PatientRescource() {
        // TODO Auto-generated constructor stub
    }
    
    @EJB(beanName="PatientServiceBean")
    private IPatientServiceRemote patientService;
    
    /**
     * Retrieves representation of an instance of PatientRescource
     * @return an instance of String
     */
    @GET
    @Path("{patient-key}")
    @Produces("application/xml")
    public PatientRepresentation getPatient(@PathParam("patient-key") String id) {
    	try {
    		long key = Long.parseLong(id);
    		PatientDTO patientDTO = patientService.getPatientByDbId(key);
    		PatientRepresentation patientRep=new PatientRepresentation(patientDTO,context);
    		return patientRep;
    	} catch (PatientServiceExn e) {
    		throw new WebApplicationException(403);
    	}
    }
    
    @GET
    @Path("by-ident")
    @Produces("application/xml")
    public PatientRepresentation getPatientByPatientId(@QueryParam("patient-id") String patientId) {
    	try {
    		long pid = Long.parseLong(patientId);
    		PatientDTO patientDTO = patientService.getPatientByPatientId(pid);
    		PatientRepresentation patientRep=new PatientRepresentation(patientDTO,context);
    		return patientRep;
    	} catch (PatientServiceExn e) {
    		throw new WebApplicationException(403);
    	}
    }
    
    @GET    
    @Produces("application/xml")
    public PatientRepresentation[] getPatientByNameDob(@QueryParam("name") String name,
    													@QueryParam("dob") String dob ) {
    	Date birthDate = DatatypeConverter.parseDate(dob).getTime();
		PatientDTO[] patientDTOs = patientService.getPatientByNameDob(name, birthDate);
		PatientRepresentation[] patientReps = new PatientRepresentation[patientDTOs.length];
		for(int i=0; i<patientDTOs.length; i++){
			patientReps[i] = new PatientRepresentation(patientDTOs[i],context);
		}
		return patientReps;
    }
    
    @SuppressWarnings("deprecation")
	@POST
    @Consumes("application/xml")
    public Response addPatient(PatientRepresentation patientRep) {
    	Date nowDate = new Date();
    	Date dob = patientRep.getDob();
		//calculate age, system do not recommend to use getYear(), getMonth(), getDate()
		int age = nowDate.getYear()-dob.getYear();
		if (nowDate.getMonth()<dob.getMonth()){
			age --;
		}
		else if (nowDate.getMonth()==dob.getMonth() && nowDate.getDate()<dob.getDate()){
			age --;
		}
    	try {
			long id = patientService.createPatient(patientRep.getName(), age, dob, patientRep.getPatientId());
			UriBuilder ub = context.getAbsolutePathBuilder().path("{id}");
			URI url = ub.build(Long.toString(id));
//			return Response.created(url).build();
			return Response.status(201).header("Location", url).build();
		} catch (PatientServiceExn e) {
//			throw new WebApplicationException();
			return Response.status(403).build();
		}
    }
    
    @DELETE
    @Path("{patient-key}")
    public void deletePatient(@PathParam("patient-key") String id, @QueryParam("name") String name){
    	try {
        	long key = Long.parseLong(id);
			patientService.deletePatient(name, key);
		} catch (PatientServiceExn e) {
    		throw new WebApplicationException(403);
		}
    }
    
    @POST
    @Consumes("application/xml")
    public Response addDrugTreatment(TreatmentRepresentation rep){
    	try {
        	DrugTreatmentType drug = rep.getDrugTreatment();
        	long patientKey = Long.parseLong(rep.getLinkPatient().getUrl().substring(22));
        	long providerId = Long.parseLong(rep.getLinkProvider().getUrl().substring(22));
			long tid = patientService.addDrugTreatment(patientKey, providerId, rep.getDiagnosis(), drug.getName(), drug.getDosage());
			UriBuilder ub = context.getAbsolutePathBuilder().path("{tid}");
			URI url = ub.build(Long.toString(tid));
			return Response.status(201).header("Location", url).build();
		} catch (PatientNotFoundExn e) {
    		throw new WebApplicationException(403);
		} catch (AddingTreatmentExn e) {
    		throw new WebApplicationException();
		}
    }
    
    @POST
    @Consumes("application/xml")
    public Response addSurgery(TreatmentRepresentation rep){
		try {
			SurgeryType surgery = rep.getSurgery();
	    	long patientKey = Long.parseLong(rep.getLinkPatient().getUrl().substring(22));
	    	long providerId = Long.parseLong(rep.getLinkProvider().getUrl().substring(22));
	    	long tid = patientService.addSurgery(patientKey, rep.getDiagnosis(), surgery.getDate(), providerId);
			UriBuilder ub = context.getAbsolutePathBuilder().path("{tid}");
			URI url = ub.build(Long.toString(tid));
			return Response.status(201).header("Location", url).build();
		} catch (PatientNotFoundExn e) {
    		throw new WebApplicationException();
		} catch (AddingTreatmentExn e) {
    		throw new WebApplicationException();
		}
    }
    
    @POST
    @Consumes("application/xml")
    public Response addRadiology(TreatmentRepresentation rep){
    	try {
    		RadiologyType radiology = rep.getRadiology();
        	long patientKey = Long.parseLong(rep.getLinkPatient().getUrl().substring(22));
        	long providerId = Long.parseLong(rep.getLinkProvider().getUrl().substring(22));
			long tid = patientService.addRadiology(patientKey, rep.getDiagnosis(), radiology.getDate(), providerId);
			UriBuilder ub = context.getAbsolutePathBuilder().path("{tid");
			URI url = ub.build(Long.toString(tid));
			return Response.status(201).header("Location", url).build();
		} catch (PatientNotFoundExn e) {
    		throw new WebApplicationException();
		} catch (AddingTreatmentExn e) {
    		throw new WebApplicationException();
		}
    }
    
    @DELETE
    @Path("deleteTreatment")
    public void deleteTreatment(@QueryParam("patient-key") String id,
									@QueryParam("treatment-id") String tid){
    	try {
			patientService.deleteTreatment(Long.parseLong(id), Long.parseLong(tid));
		} catch (NumberFormatException e) {
    		throw new WebApplicationException();
		} catch (PatientNotFoundExn e) {
    		throw new WebApplicationException();
		} catch (TreatmentNotFoundExn e) {
    		throw new WebApplicationException();
		} catch (PatientServiceExn e) {
    		throw new WebApplicationException();
		}
    }
    
    
}