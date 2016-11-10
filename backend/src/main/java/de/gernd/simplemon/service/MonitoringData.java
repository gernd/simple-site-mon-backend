package de.gernd.simplemon.service;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Synchronizes access to monitored URLs and monitor results
 */
@Slf4j
public class MonitoringData {

    private final List<MonitoredUrl> monitoredUrls = new LinkedList<>();
    private final Map<Integer, List<MonitoringResult>> monitoredUrlIdsToResults = new HashMap<>();
    private static int monitoredUrlIdCounter = 1;

    public synchronized void addUrl(final String url) {
        monitoredUrls.add(MonitoredUrl.builder().id(monitoredUrlIdCounter++).url(url).build());
    }

    /**
     * @return A shallow copy containing immutable MonitoredUrl instances. It is safe to iterate or change the list that is returned by this method.
     */
    public synchronized List<MonitoredUrl> getMonitoredUrls() {
        List<MonitoredUrl> clonedList = new LinkedList<>();
        for (MonitoredUrl monitoredUrl : monitoredUrls) {
            clonedList.add(MonitoredUrl.builder().url(monitoredUrl.getUrl()).id(monitoredUrl.getId()).build());
        }

        return clonedList;
    }

    /**
     * Updates the monitoring results with data from the last monitoring run
     *
     * @param results The results of the last monitoring run
     */
    public synchronized void updateMonitoringResults(List<MonitoringResult> results) {
        results.forEach(result -> {
            log.info("Adding monitoring result: {}", result);
            if (!monitoredUrlIdsToResults.containsKey(result.getUrlToMonitor().getId())) {
                monitoredUrlIdsToResults.put(result.getUrlToMonitor().getId(), new LinkedList<>());
            }
            monitoredUrlIdsToResults.get(result.getUrlToMonitor().getId()).add(result);
        });
    }

    /**
     * @param id identifier of the URL for which the monitoring results should be fetched
     * @return A shallow copy of all immutable monitoring results, It is safe to iterate or change the list that is returned by this method.
     */
    public synchronized List<MonitoringResult> getMonitoringResults(final int id) {
        if (!monitoredUrlIdsToResults.containsKey(id)) {
            return Collections.EMPTY_LIST;
        }
        List<MonitoringResult> clonedResults = new LinkedList<>();
        monitoredUrlIdsToResults.get(id).forEach(monResult -> clonedResults.add(monResult));
        return clonedResults;
    }
}
