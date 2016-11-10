package de.gernd.simplemon.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Attempts to reach all given urls and collects the results
 */
@Slf4j
public class ScheduledMonitoringJob implements Runnable {

    /**
     * Encapsulates the HTTP request and monitoring for a given URL
     */
    private static class MonitorTask implements Callable<MonitoringResult> {

        private final MonitoredUrl urlToMonitor;

        public MonitorTask(final MonitoredUrl urlToMonitor) {
            this.urlToMonitor = urlToMonitor;
        }

        @Override
        public MonitoringResult call() throws Exception {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(urlToMonitor.getUrl(), HttpMethod.GET, null, String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                return MonitoringResult.builder().isUp(true).urlToMonitor(urlToMonitor).build();
            } else {
                log.info("Request for url {} returned status code {}", urlToMonitor.getUrl(), response.getStatusCode());
                return MonitoringResult.builder().isUp(false).urlToMonitor(urlToMonitor).build();
            }
        }
    }

    final MonitoringData monitoringData;
    final ExecutorService executorService = Executors.newCachedThreadPool();


    public ScheduledMonitoringJob(MonitoringData monitoringData) {
        this.monitoringData = monitoringData;
    }

    @Override
    public void run() {
        List<MonitoredUrl> urlsToMonitor = monitoringData.getMonitoredUrls();
        List<Future<MonitoringResult>> results = new LinkedList<Future<MonitoringResult>>();

        for (MonitoredUrl urlToMonitor : urlsToMonitor) {
            Future<MonitoringResult> monitoringResult = executorService.submit(new MonitorTask(urlToMonitor));
            results.add(monitoringResult);
        }

        // collect results and map exception to error results
        List<MonitoringResult> mappedResults = new LinkedList<>();
        for (Future<MonitoringResult> monitoringResult : results) {
            try {
                MonitoringResult result = monitoringResult.get();
                mappedResults.add(result);
            } catch (InterruptedException | ExecutionException e) {
                log.error("Error during monitoring execution: {}", e.getMessage());
                // TODO add mapped result with url and id from failed request
            }
        }

        monitoringData.updateMonitoringResults(mappedResults);
    }
}
