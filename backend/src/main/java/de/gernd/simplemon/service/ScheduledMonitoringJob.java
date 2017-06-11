package de.gernd.simplemon.service;

import de.gernd.simplemon.endpoints.dto.MonitoredUrl;
import de.gernd.simplemon.endpoints.dto.MonitoringResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        private final RestTemplate restTemplate;
        private long timeNeededForRequestMs;

        public MonitorTask(final MonitoredUrl urlToMonitor) {
            this.urlToMonitor = urlToMonitor;
            // set up Rest template capable of benchmarking HTTP requests
            restTemplate = new RestTemplate();
            List<ClientHttpRequestInterceptor> requestInterceptors = new ArrayList<>();
            requestInterceptors.add(new ClientHttpRequestInterceptor() {
                @Override
                public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                    ClientHttpResponse response = execution.execute(request, body);
                    // TODO (GPO) reactivate stopwatch
                    timeNeededForRequestMs = 0;
                    return response;
                }
            });
            restTemplate.setInterceptors(requestInterceptors);
        }

        @Override
        public MonitoringResult call() throws Exception {
            ResponseEntity<String> response = restTemplate.exchange(urlToMonitor.getUrl(), HttpMethod.GET, null, String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                return MonitoringResult.builder().isUp(true).urlToMonitor(urlToMonitor).timeNeededForRequest(timeNeededForRequestMs).build();
            } else {
                log.info("Request for url {} returned status code {}", urlToMonitor.getUrl(), response.getStatusCode());
                return MonitoringResult.builder().isUp(false).urlToMonitor(urlToMonitor).build();
            }
        }
    }

    final ExecutorService executorService = Executors.newCachedThreadPool();


    @Override
    public void run() {

        /*
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
        */

    }
}
