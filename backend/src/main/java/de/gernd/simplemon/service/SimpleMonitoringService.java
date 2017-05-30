package de.gernd.simplemon.service;

import de.gernd.simplemon.config.MonitoringConfig;
import de.gernd.simplemon.endpoints.dto.MonitoredUrl;
import de.gernd.simplemon.model.MonitoredEntityRepository;
import de.gernd.simplemon.model.entities.MonitoredResourceEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    /**
     * Start monitoring a web resource
     *
     * @param url URL of the website to monitor
     */
    public synchronized void startMonitoring(String url) {
        log.info("Request to start monitoring " + url);
        monitoredEntityRepository.save(MonitoredResourceEntity.builder().url(url).build());
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
     * Monitors all registered resources
     */
    @Scheduled(fixedDelay = 3000)
    private void monitor() {
        Iterable<MonitoredResourceEntity> monitoredResourceEntities = monitoredEntityRepository.findAll();
        monitoredResourceEntities.forEach(
                e -> {
                    log.info("Monitoring " + e);
                });
    }
}
