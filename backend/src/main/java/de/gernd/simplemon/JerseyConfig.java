package de.gernd.simplemon;

import de.gernd.simplemon.endpoints.SimpleMonitoringEndpoint;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig{

    public JerseyConfig(){
        register(SimpleMonitoringEndpoint.class);
    }
}
