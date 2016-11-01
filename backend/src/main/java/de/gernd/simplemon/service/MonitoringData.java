package de.gernd.simplemon.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Synchronizes access to monitored URLs and monitor results
 */
public class MonitoringData {

    private final List<String> monitoredUrls = new LinkedList<>();
    private final Map<String, List<MonitoringResult>> urlToMonitoringResults = new HashMap<>();

    public synchronized void addUrl(final String url) {
        monitoredUrls.add(url);
    }

    /**
     * @return A deep copy of all monitored Urls, It is safe to iterate or change the list that is returned by this method.
     */
    public synchronized List<String> getMonitoredUrls() {
        List<String> clonedList = new LinkedList<>();
        for (String url : monitoredUrls) {
            clonedList.add(url);
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
            System.out.println("Adding result for URL " + result.getUrl());
            if(!urlToMonitoringResults.containsKey(result.getUrl())){
              urlToMonitoringResults.put(result.getUrl(), new LinkedList<>());
            }
            urlToMonitoringResults.get(result.getUrl()).add(result);
        });
    }
}
