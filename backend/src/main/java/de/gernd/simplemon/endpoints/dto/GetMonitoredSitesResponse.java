package de.gernd.simplemon.endpoints.dto;

import de.gernd.simplemon.service.MonitoredUrl;
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
