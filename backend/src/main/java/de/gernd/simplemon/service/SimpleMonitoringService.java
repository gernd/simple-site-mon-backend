package de.gernd.simplemon.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Implements the simple website monitoring functionality
 */
@Component
public class SimpleMonitoringService {

    private final Logger log = Logger.getLogger(SimpleMonitoringService.class);
    private final List<String> monitoredSites = new LinkedList<>();

    /**
     * Start monitoring a website
     *
     * @param url URL of the website to monitor
     */
    public void startMonitoring(String url) {
        log.info("Request to start monitoring " + url);
        monitoredSites.add(url);
    }

    public List<String> getMonitoredSites() {
        return monitoredSites;
    }
}
