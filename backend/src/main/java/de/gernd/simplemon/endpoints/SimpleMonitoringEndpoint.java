package de.gernd.simplemon.endpoints;

import de.gernd.simplemon.endpoints.dto.AddSiteToMonitorRequest;
import de.gernd.simplemon.endpoints.dto.GetMonitoredSitesResponse;
import de.gernd.simplemon.endpoints.dto.MonitoredUrl;
import de.gernd.simplemon.service.SimpleMonitoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
public class SimpleMonitoringEndpoint {

    @Autowired
    private SimpleMonitoringService monitoringService;

    /**
     * Method for retrieving all currently monitored sites
     *
     * @return All currently monitored sites
     */
    @GetMapping(path = "/monitored-sites")
    public ResponseEntity<GetMonitoredSitesResponse> getMonitoredSites() {
        log.info("Request to get all monitored resources");
        List<MonitoredUrl> monitoredSites = monitoringService.getMonitoredSites();
        GetMonitoredSitesResponse response =
                GetMonitoredSitesResponse.builder().monitoredSites(monitoredSites).build();
        return ResponseEntity.ok(response);
    }

    /**
     * Method for adding a new web resource to be monitored
     * @param addSiteToMonitorRequest Request containing the web resource to be monitored
     */
    @PostMapping(path = "/monitored-sites")
    public ResponseEntity addSite(@RequestBody AddSiteToMonitorRequest addSiteToMonitorRequest) {
        log.info("Request to add resource {} for monitoring", addSiteToMonitorRequest.url);
        monitoringService.startMonitoring(addSiteToMonitorRequest.url);
        return ResponseEntity.created(URI.create("DummyURI")).build();
    }

    /**
     * Method for retrieving monitor results for a certain URL
     *
     * @param id The URL identifier to fetch monitoring results for
     * @return The monitoring results for the given url
     */
    @GetMapping(path = "/monitor_results/{id}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public void getMonitorResults(@PathParam("id") final int id) {
        log.info("Request for monitoring results for {}", id);
        return;
    }
}
