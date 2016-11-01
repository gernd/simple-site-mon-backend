package de.gernd.simplemon.endpoints;

import de.gernd.simplemon.endpoints.model.AddSiteToMonitorRequest;
import de.gernd.simplemon.endpoints.model.GetMonitoredSitesResponse;
import de.gernd.simplemon.service.MonitoringResult;
import de.gernd.simplemon.service.SimpleMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/")
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
    @Path("/monitored-sites")
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
    @Path("/monitored-sites")
    @POST
    public Response addSite(AddSiteToMonitorRequest addSiteToMonitorRequest){
        monitoringService.startMonitoring(addSiteToMonitorRequest.url);
        return Response.ok().build();
    }

    /**
     * Method for retrieving monitor results for a certain URL
     *
     * @param url The URL to fetch the results for
     * @return The monitoring results for the given url
     */
    @Path("/results")
    @GET
    public Response getMonitorResults() {
        // hardcoded ATM
        final String url = "http://www.google.de";
        GetMonitoredSitesResponse response = new GetMonitoredSitesResponse();
        List<MonitoringResult> monitoringResults = monitoringService.getMonitoringResults(url);
        return Response.ok(monitoringResults).build();
    }

}
