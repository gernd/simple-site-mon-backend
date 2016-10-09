package de.gernd.simplemon.endpoints;

import de.gernd.simplemon.endpoints.model.GetMonitoredSitesResponse;
import de.gernd.simplemon.service.SimpleMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

@Component
@Path("/monitored-sites")
@Produces("application/json")
public class SimpleMonitoringEndpoint {

    @Autowired
    private SimpleMonitoringService monitoringService;

    @Path("/")
    @GET
    public Response monitor() {
        GetMonitoredSitesResponse response = new GetMonitoredSitesResponse();
        List<String> monitoredSites = new LinkedList<>();
        monitoredSites.add("Site 1");
        monitoredSites.add("Site 2");
        response.setMonitoredSites(monitoredSites);
        return Response.ok(monitoredSites).build();
    }
}
