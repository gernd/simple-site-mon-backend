package de.gernd.simplemon.service;

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
public class ScheduledMonitoringJob implements Runnable {


    /**
     * Encapsulates the HTTP request and monitoring for a given URL
     */
    private static class MonitorTask implements Callable<String> {

        private final String url;

        public MonitorTask(final String url) {
            this.url = url;
        }

        @Override
        public String call() throws Exception {
            System.out.println("Request start for URL " + url);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            System.out.println("Request done for URL " + url);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                return "200 OK for URL " + url;
            } else {
                return "Did not work for URL " + url;
            }
        }
    }

    final MonitoringData monitoringData;
    final ExecutorService executorService = Executors.newCachedThreadPool();


    public ScheduledMonitoringJob(MonitoringData monitoringData) {
        this.monitoringData = monitoringData;
        System.out.println("Monitoring Task instantiated");
    }

    @Override
    public void run() {
        System.out.println("Monitoring service running");

        List<String> urlsToMonitor = monitoringData.getMonitoredUrls();
        List<Future<String>> results = new LinkedList<Future<String>>();

        for (String urlToMonitor : urlsToMonitor) {
            Future<String> monitoringResult = executorService.submit(new MonitorTask(urlToMonitor));
            results.add(monitoringResult);
        }


        // collect results
        for (Future<String> monitoringResult : results) {
            try {
                System.out.println("Result of monitoring: " + monitoringResult.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

    }
}
