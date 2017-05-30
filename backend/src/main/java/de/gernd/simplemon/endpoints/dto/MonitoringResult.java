package de.gernd.simplemon.endpoints.dto;

import de.gernd.simplemon.endpoints.dto.MonitoredUrl;
import lombok.*;

@Builder
@Getter
@ToString
public class MonitoringResult {
    private boolean isUp;
    private MonitoredUrl urlToMonitor;
    private long timeNeededForRequest;
}
