package edu.stevens.cs548.clinic.service.web.rest.resources;

import java.net.URI;
import java.util.Date;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
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
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceRemote;
import edu.stevens.cs548.clinic.service.web.rest.PatientRepresentation;

@Path("/patient")
public class PatientRescource {
    @SuppressWarnings("unused")
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
    @Path("{id}")
    @Produces("application/xml")
    public PatientRepresentation getPatient(@PathParam("id") String id) {
    	try {
    		long key = Long.parseLong(id);
    		PatientDTO patientDTO = patientService.getPatientByDbId(key);
    		PatientRepresentation patientRep=new PatientRepresentation(patientDTO,context);
    		return patientRep;
    	} catch (PatientServiceExn e) {
    		throw new WebApplicationException();
    	}
    }
    
    @GET
    @Path("patientId")
    @Produces("application/xml")
    public PatientRepresentation getPatientByPatientId(@QueryParam("pid") String patientId) {
    	try {
    		long pid = Long.parseLong(patientId);
    		PatientDTO patientDTO = patientService.getPatientByPatientId(pid);
    		PatientRepresentation patientRep=new PatientRepresentation(patientDTO,context);
    		return patientRep;
    	} catch (PatientServiceExn e) {
    		throw new WebApplicationException();
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
			return Response.created(url).build();
		} catch (PatientServiceExn e) {
			throw new WebApplicationException();
		}
    }
    
}