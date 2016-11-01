package de.gernd.simplemon.service;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
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
    private final List<String> monitoredSites = new LinkedList<>();

    /**
     * number of threads available for monitoring tasks
     */
    private final int NUMBER_OF_THREADS = 3;

    /**
     * number of second between each monitoring
     */
    private final int MONITORING_INTERVAL_SECONDS = 5;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(NUMBER_OF_THREADS);

    /**
     * Start monitoring a website
     *
     * @param url URL of the website to monitor
     */
    public synchronized void startMonitoring(String url) {
        log.info("Request to start monitoring " + url);
        monitoredSites.add(url);
        final Runnable monitoringTask = () -> {
            System.out.println("Monitoring " + url);
            // TODO add Rest Template
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                System.out.println("Received 200");
            } else {
                System.out.println("Received status code " + response.getStatusCode().getReasonPhrase());
            }
        };
        executorService.scheduleAtFixedRate(monitoringTask, MONITORING_INTERVAL_SECONDS, MONITORING_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    public List<String> getMonitoredSites() {
        return monitoredSites;
    }
}
