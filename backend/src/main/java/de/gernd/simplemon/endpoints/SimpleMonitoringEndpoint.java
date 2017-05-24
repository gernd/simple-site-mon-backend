package de.gernd.simplemon.endpoints;

import de.gernd.simplemon.endpoints.dto.AddSiteToMonitorRequest;
import de.gernd.simplemon.endpoints.dto.GetMonitoredSitesResponse;
import de.gernd.simplemon.service.MonitoredUrl;
import de.gernd.simplemon.service.MonitoringResult;
import de.gernd.simplemon.service.SimpleMonitoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Component
@Path("/")
@Produces("application/json")
@Consumes("application/json")
@Slf4j
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
        List<MonitoredUrl> monitoredSites = monitoringService.getMonitoredSites();
        response.monitoredSites = monitoredSites;
        return Response.ok(monitoredSites).build();
    }

    /**
     * Method for retrieving all currently monitored sites
     */
    @Path("/monitored-sites")
    @POST
    public Response addSite(AddSiteToMonitorRequest addSiteToMonitorRequest) {
        log.info("Request to add site {} for monitoring", addSiteToMonitorRequest.url);
        monitoringService.startMonitoring(addSiteToMonitorRequest.url);
        return Response.created(URI.create("DummyURI")).build();
    }

    /**
     * Method for retrieving monitor results for a certain URL
     *
     * @param id The URL identifier to fetch monitoring results for
     * @return The monitoring results for the given url
     */
    @Path("/monitor_results/{id}")
    @GET
    public Response getMonitorResults(@PathParam("id") final int id) {
        log.info("Request for monitoring results for {}", id);
        GetMonitoredSitesResponse response = new GetMonitoredSitesResponse();
        List<MonitoringResult> monitoringResults = monitoringService.getMonitoringResults(id);
        return Response.ok(monitoringResults).build();
    }

}
