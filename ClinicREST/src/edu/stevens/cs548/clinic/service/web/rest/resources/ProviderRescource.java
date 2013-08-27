package edu.stevens.cs548.clinic.service.web.rest.resources;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import edu.stevens.cs548.clinic.service.dto.ProviderDTO;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceRemote;
import edu.stevens.cs548.clinic.service.web.rest.ProviderRepresentation;
import edu.stevens.cs548.clinic.service.web.rest.TreatmentRepresentation;

@Path("/provider")
public class ProviderRescource {
    @Context
    private UriInfo context;

    /**
     * Default constructor. 
     */
    public ProviderRescource() {
        // TODO Auto-generated constructor stub
    }
    
    @EJB(beanName="ProviderServiceBean")
    private IProviderServiceRemote providerService;

    @GET
    @Path("{providerId}")
    @Produces("application/xml")
    public ProviderRepresentation getProviderByProviderId(@PathParam("providerId") String providerId) {
        try {
        	long pid = Long.parseLong(providerId);
			ProviderDTO providerDTO = providerService.getProviderByProviderId(pid);
			ProviderRepresentation providerRepresentation = new ProviderRepresentation(providerDTO, context);
			return providerRepresentation;
		} catch (NumberFormatException e) {
    		throw new WebApplicationException();
		} catch (ProviderServiceExn e) {
    		throw new WebApplicationException();
		}
    }
    
    @GET
    @Path("getTreatment")
    public TreatmentRepresentation getTreatment(@QueryParam("provider-Id") String providerId, @QueryParam("treatmentURI") String uri){
    	try {
    		long pid = Long.parseLong(providerId);
        	long tid = Long.parseLong(uri.substring(23));
        	long tids[] = new long [1];
        	tids[0]=tid;
			TreatmentDto[] treatmentDTOs = providerService.getTreatments(pid, tids);
			TreatmentRepresentation treatmentRepresentation = new TreatmentRepresentation(treatmentDTOs[0],context);
			return treatmentRepresentation;
		} catch (ProviderServiceExn e) {
    		throw new WebApplicationException();
		}
    }



}