package de.gernd.simplemon.service;

import java.util.LinkedList;
import java.util.List;

/**
 * Synchronizes access to monitored URLs and monitor results
 */
public class MonitoringData {

    private final List<String> monitoredUrls = new LinkedList<>();

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
}
