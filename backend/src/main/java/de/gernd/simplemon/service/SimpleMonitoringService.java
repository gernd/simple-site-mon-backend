package de.gernd.simplemon.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Implements the simple website monitoring functionality
 */
@Component
public class SimpleMonitoringService {

    private final Logger log = Logger.getLogger(SimpleMonitoringService.class);

    /**
     * Start monitoring a website
     * @param url URL of the website to monitor
     */
    public void startMonitoring(String url){
        log.info("Request to start monitoring " + url);
    }
}
