package de.gernd.simplemon.service;

import lombok.*;

@Builder
@Getter
@ToString
public class MonitoringResult {
    private boolean isUp;
    private MonitoredUrl urlToMonitor;
}
