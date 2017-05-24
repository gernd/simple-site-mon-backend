package de.gernd.simplemon.endpoints.dto;

import de.gernd.simplemon.service.MonitoredUrl;

import java.util.List;

/**
 * DTO for monitored sites
 */
public class GetMonitoredSitesResponse {
    public List<MonitoredUrl> monitoredSites;
}
