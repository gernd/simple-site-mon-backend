package de.gernd.simplemon;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path("/simple-mon")
public class SimpleMonitoringEndpoint {

    @Path("/test")
    @GET
    public String monitor(){
        return "It is the monitoring endpoint";
    }
}
