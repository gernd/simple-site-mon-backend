package de.gernd.simplemon.service;

public class MonitoringResult {
    private final boolean isUp;
    private final String url;

    public MonitoringResult(boolean isUp, String url) {
        this.isUp = isUp;
        this.url = url;
    }

    public boolean isUp() {
        return isUp;
    }

    public String getUrl(){
        return url;
    }

}
