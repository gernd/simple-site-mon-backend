package de.gernd.simplemon.endpoints;

import de.gernd.simplemon.service.SimpleMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Component
@Path("/simple-mon")
public class SimpleMonitoringEndpoint {

    @Autowired
    private SimpleMonitoringService monitoringService;

    @Path("/test")
    @GET
    public Response monitor(){
        monitoringService.startMonitoring("www.github.com");
        return Response.ok("This is the monitoring service").build();
    }
}
