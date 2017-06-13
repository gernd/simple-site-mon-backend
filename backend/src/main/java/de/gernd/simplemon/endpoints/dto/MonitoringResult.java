package de.gernd.simplemon.endpoints.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MonitoringResult {
    private boolean isUp;
    private long timeNeededForRequest;
}
