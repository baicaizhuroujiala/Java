package edu.stevens.cs548.clinic.service.web.rest;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.web.rest.data.LinkType;
import edu.stevens.cs548.clinic.service.web.rest.data.TreatmentType;

public class TreatmentRepresentation extends TreatmentType {
	
	public TreatmentRepresentation(TreatmentDto dto, UriInfo uriInfo) {
		//TODO: set patient link
		this.id = dto.getTreatmentId();
		this.diagnosis = dto.getDiagnosis();
		this.drugTreatment.setName(dto.getDrugTreatment().getName());
		this.drugTreatment.setDosage(dto.getDrugTreatment().getDosage());
		this.surgery.setDate(dto.getSurgery().getDate());
		UriBuilder ub = uriInfo.getBaseUriBuilder();
		String providerURI = ub.path("provider").path("{providerId}").build(Long.toString(dto.getProviderId())).toString();
		LinkType link = new LinkType();
		link.setUrl(providerURI);
		link.setRelation(Representation.RELATION_PROVIDER);
		link.setMediaType(Representation.MEDIA_TYPE);
		this.provider = link;
	}

	public TreatmentRepresentation() {
		super();
	}

	public LinkType getLinkPatient() {
		return this.getPatient();
	}

	public LinkType getLinkProvider() {
		return this.getProvider();
	}
	
	
}

