package de.gernd.simplemon.endpoints;

import de.gernd.simplemon.endpoints.dto.AddSiteToMonitorRequest;
import de.gernd.simplemon.endpoints.dto.GetMonitoredSiteResponse;
import de.gernd.simplemon.endpoints.dto.GetMonitoredSitesResponse;
import de.gernd.simplemon.endpoints.dto.MonitoredUrl;
import de.gernd.simplemon.model.entities.MonitoringResult;
import de.gernd.simplemon.service.SimpleMonitoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
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
     *
     * @param addSiteToMonitorRequest Request containing the web resource to be monitored
     */
    @PostMapping(path = "/monitored-sites")
    public ResponseEntity addSite(@RequestBody AddSiteToMonitorRequest addSiteToMonitorRequest) {
        log.info("Request to add resource {} for monitoring", addSiteToMonitorRequest.getUrl());
        long monitoredResourceId = monitoringService.startMonitoring(addSiteToMonitorRequest.getUrl());

        // build URL for created resource
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        return ResponseEntity.created(URI.create(
                builder.path("/" + String.valueOf(monitoredResourceId)).toUriString())).build();
    }

    /**
     * Method for retrieving monitor results for a monitored URL
     *
     * @param id The URL identifier to fetch monitoring results for
     * @return The monitoring results for the given url
     */
    @GetMapping(path = "/monitored-sites/{id}")
    public ResponseEntity<GetMonitoredSiteResponse> getMonitoredSiteDetails(@PathVariable("id") final Long id) {
        log.info("Request for monitoring results for {}", id);
        List<de.gernd.simplemon.endpoints.dto.MonitoringResult> resultDtos = Collections.synchronizedList(new ArrayList<>());
        List<MonitoringResult> resultEntities = monitoringService.getMonitoringResults(id);
        resultEntities.forEach(monitoringResult -> resultDtos.add(
                de.gernd.simplemon.endpoints.dto.MonitoringResult.builder().
                        timeNeededForRequest(monitoringResult.getResponseTime()).build()));
        GetMonitoredSiteResponse response = GetMonitoredSiteResponse.builder().id(id).monitoringResults(resultDtos).build();
        return ResponseEntity.ok(response);
    }
}
