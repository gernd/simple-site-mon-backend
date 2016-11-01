package de.gernd.simplemon.service;

public class MonitoringResult {
    private final boolean isUp;

    public MonitoringResult(boolean isUp) {
        this.isUp = isUp;
    }

    public boolean isUp() {
        return isUp;
    }


}
