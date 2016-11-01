package de.gernd.simplemon.service;

import java.util.*;

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
            clonedList.add(new String(url));
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

    /**
     * @return A shallow copy of all immutable monitoring results, It is safe to iterate or change the list that is returned by this method.
     */
    public synchronized List<MonitoringResult> getMonitoringResults(final String url){
        if(!urlToMonitoringResults.containsKey(url)){
            return Collections.EMPTY_LIST;
        }

        List<MonitoringResult> clonedResults = new LinkedList<>();
        urlToMonitoringResults.get(url).forEach(monResult -> clonedResults.add(monResult));
        return clonedResults;
    }
}
