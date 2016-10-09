package de.gernd.simplemon.endpoints.model;

import java.util.List;

/**
 * DTO for monitored sites
 */
public class GetMonitoredSitesResponse {

    public List<String> monitoredSites;

    public List<String> getMonitoredSites() {
        return monitoredSites;
    }

    public void setMonitoredSites(List<String> monitoredSites) {
        this.monitoredSites = monitoredSites;
    }
}
