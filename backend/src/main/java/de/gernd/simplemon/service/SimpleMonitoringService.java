package de.gernd.simplemon.service;

import de.gernd.simplemon.config.MonitoringConfig;
import de.gernd.simplemon.endpoints.dto.MonitoredUrl;
import de.gernd.simplemon.model.MonitoredEntityRepository;
import de.gernd.simplemon.model.entities.MonitoredResourceEntity;
import de.gernd.simplemon.model.entities.MonitoringResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements the simple website monitoring functionality
 */
@Component
@Slf4j
public class SimpleMonitoringService {

    @Autowired
    private MonitoringConfig monitoringConfig;

    @Autowired
    private MonitoredEntityRepository monitoredEntityRepository;

    final RestTemplate restTemplate = new RestTemplate();

    /**
     * Start monitoring a web resource
     *
     * @param url URL of the website to monitor
     * @return the id of the monitored resource
     */
    public long startMonitoring(String url) {
        log.info("Request to start monitoring " + url);
        MonitoredResourceEntity createdEntity = monitoredEntityRepository.save(MonitoredResourceEntity.builder().url(url).build());
        return createdEntity.getId();
    }

    /**
     * Returns all monitored resources
     *
     * @return all monitored resources
     */
    public List<MonitoredUrl> getMonitoredSites() {
        Iterable<MonitoredResourceEntity> entities = monitoredEntityRepository.findAll();
        List<MonitoredUrl> dtos = Collections.synchronizedList(new ArrayList<>());
        entities.forEach(e -> dtos.add(MonitoredUrl.builder().url(e.getUrl()).id(e.getId()).build()));
        return dtos;
    }

    /**
     * Fetches monitoring results for the monitored entity
     *
     * @param monitoredUrlId id of the monitored resource to get the monitoring results
     * @return MonitoringResults for the monitored entity
     */
    public List<MonitoringResult> getMonitoringResults(long monitoredUrlId) {
        MonitoredResourceEntity monitoredResource = monitoredEntityRepository.findOne(monitoredUrlId);
        if (monitoredResource == null) {
            return Collections.emptyList();
        } else {
            return monitoredResource.getMonitoringResults();
        }

    }

    /**
     * Monitors all registered resources
     */
    @Scheduled(fixedDelay = 3000)
    private void monitor() {
        Iterable<MonitoredResourceEntity> monitoredResourceEntities = monitoredEntityRepository.findAll();
        monitoredResourceEntities.forEach(
                e -> {
                    log.info("Monitoring " + e);
                    StopWatch stopWatch = new StopWatch();
                    stopWatch.start();
                    restTemplate.exchange(e.getUrl(), HttpMethod.GET, null, String.class);
                    stopWatch.stop();
                    MonitoringResult monitoringResult = MonitoringResult.builder().responseTime(stopWatch.getTotalTimeMillis()).build();
                    e.addMonitoringResult(monitoringResult);
                    monitoredEntityRepository.save(e);
                });
    }
}
