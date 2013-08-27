package edu.stevens.cs548.clinic.service.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.stevens.CS548.clinic.domain.Provider;

@XmlRootElement(name="provider-dto", namespace="http://www.example.org/clinic/schemas/provider")
public class ProviderDTO {
	public long id;
	
	@XmlElement(name="provider-id")
	public long providerId;
	@XmlElement(required=true)
	public String name;
	@XmlElement(required=true)
	public String specialization;
	public long[] treatments;

	public ProviderDTO(){
		
	}
	
	public ProviderDTO(Provider provider){
		this.id=provider.getId();
		this.providerId=provider.getProviderId();
		this.name=provider.getName();
		this.specialization=provider.getSpecialization();
		List<Long> tids=provider.getTreatmentIds();
		this.treatments=new long[tids.size()];
		for(int i=0; i<this.treatments.length; i++){
			this.treatments[i]=tids.get(i);
		}
	}
}

