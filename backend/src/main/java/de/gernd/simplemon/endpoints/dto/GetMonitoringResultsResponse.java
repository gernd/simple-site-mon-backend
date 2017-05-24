package de.gernd.simplemon.endpoints.dto;

import de.gernd.simplemon.service.MonitoringData;
import de.gernd.simplemon.service.MonitoringResult;

import java.util.List;

public class GetMonitoringResultsResponse {
    public List<MonitoringResult> monitoringResults;
}
