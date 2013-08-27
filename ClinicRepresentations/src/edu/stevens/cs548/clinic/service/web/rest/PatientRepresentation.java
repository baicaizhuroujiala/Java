package edu.stevens.cs548.clinic.service.web.rest;

import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import edu.stevens.cs548.clinic.service.dto.PatientDTO;
import edu.stevens.cs548.clinic.service.web.rest.data.LinkType;
import edu.stevens.cs548.clinic.service.web.rest.data.PatientType;

public class PatientRepresentation extends PatientType {
	
	public List<LinkType> getLinksTreatments() {
		return this.getTreatments();
	}
	
	public PatientRepresentation (){
		super();
	}
	
	public PatientRepresentation (PatientDTO dto, UriInfo uriInfo){
		this.id = dto.id;
		this.patientId = dto.patientId;
		this.name = dto.name;
		this.dob = dto.brithDate;
		
		long[] treatments = dto.treatments;
		//get base uri
		UriBuilder ub = uriInfo.getBaseUriBuilder();
		//add treatment
		ub.path("treatment");
		/*
		 * Call getTreatments to initialize empty list.
		 */
		List<LinkType> links = this.getTreatments();
		for (long t : treatments) {
			LinkType link = new LinkType();
			//add treatment ID
			UriBuilder ubTreatment = ub.clone().path("{tid}");
			String treatmentURI = ubTreatment.build(Long.toString(t)).toString();
			link.setUrl(treatmentURI);
			link.setRelation(Representation.RELATION_TREATMENT);
			link.setMediaType(Representation.MEDIA_TYPE);
			
			links.add(link);
		}
	}
}
