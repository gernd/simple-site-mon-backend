package de.gernd.simplemon.endpoints.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO for monitored sites
 */
@Data
@Builder
public class GetMonitoredSitesResponse {
    private List<MonitoredUrl> monitoredSites;
}
