package de.gernd.simplemon.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Implements the simple website monitoring functionality
 */
@Component
public class SimpleMonitoringService {

    private final Logger log = Logger.getLogger(SimpleMonitoringService.class);

    private final MonitoringData monitoringData = new MonitoringData();

    /**
     * number of threads available for the monitoring task
     */
    private final int NUMBER_OF_THREADS = 1;

    /**
     * number of second between each monitoring
     */
    private final int MONITORING_INTERVAL_SECONDS = 5;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(NUMBER_OF_THREADS);

    public SimpleMonitoringService() {
        // start monitoring
        executorService.scheduleAtFixedRate(new ScheduledMonitoringJob(monitoringData), MONITORING_INTERVAL_SECONDS, MONITORING_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * Start monitoring a website
     *
     * @param url URL of the website to monitor
     */
    public synchronized void startMonitoring(String url) {
        log.info("Request to start monitoring " + url);
        monitoringData.addUrl(url);
    }

    public List<MonitoredUrl> getMonitoredSites() {
        return monitoringData.getMonitoredUrls();
    }

    public List<MonitoringResult> getMonitoringResults(final String url){
        return monitoringData.getMonitoringResults(url);
    }
}
