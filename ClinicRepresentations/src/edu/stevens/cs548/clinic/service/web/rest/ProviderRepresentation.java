package edu.stevens.cs548.clinic.service.web.rest;

import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import edu.stevens.cs548.clinic.service.dto.ProviderDTO;
import edu.stevens.cs548.clinic.service.web.rest.data.LinkType;
import edu.stevens.cs548.clinic.service.web.rest.data.ProviderType;

public class ProviderRepresentation extends ProviderType {
	
	public List<LinkType> getLinksTreatments() {
		return this.getTreatments();
	}
	
	public ProviderRepresentation (){
		super();
	}

	public ProviderRepresentation(ProviderDTO providerDTO, UriInfo context) {
		this.id=providerDTO.id;
		this.providerId=providerDTO.providerId;
		this.name=providerDTO.name;
		this.specialization=providerDTO.specialization;
		long[] treatments = providerDTO.treatments;
		//get base uri
		UriBuilder ub = context.getBaseUriBuilder();
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
