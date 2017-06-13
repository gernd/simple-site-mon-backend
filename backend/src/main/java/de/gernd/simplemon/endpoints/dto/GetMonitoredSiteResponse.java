package de.gernd.simplemon.endpoints.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetMonitoredSiteResponse {
    private long id;
    private List<MonitoringResult> monitoringResults;
}
