package de.gernd.simplemon.endpoints;

import de.gernd.simplemon.endpoints.model.AddSiteToMonitorRequest;
import de.gernd.simplemon.endpoints.model.GetMonitoredSitesResponse;
import de.gernd.simplemon.service.SimpleMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/monitored-sites")
@Produces("application/json")
@Consumes("application/json")
public class SimpleMonitoringEndpoint {

    @Autowired
    private SimpleMonitoringService monitoringService;


    /**
     * Method for retrieving all currently monitored sites
     *
     * @return All currently monitored sites
     */
    @Path("/")
    @GET
    public Response getMonitoredSites() {
        GetMonitoredSitesResponse response = new GetMonitoredSitesResponse();
        List<String> monitoredSites = monitoringService.getMonitoredSites();
        response.monitoredSites = monitoredSites;
        return Response.ok(monitoredSites).build();
    }

    /**
     * Method for retrieving all currently monitored sites
     */
    @Path("/")
    @POST
    public Response addSite(AddSiteToMonitorRequest addSiteToMonitorRequest){
        monitoringService.startMonitoring(addSiteToMonitorRequest.url);
        return Response.ok().build();
    }


}
