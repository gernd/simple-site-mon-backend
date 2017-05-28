package de.gernd.simplemon.service;

import de.gernd.simplemon.config.MonitoringConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Implements the simple website monitoring functionality
 */
@Component
@Slf4j
public class SimpleMonitoringService implements InitializingBean {

    private final MonitoringData monitoringData = new MonitoringData();

    /**
     * number of threads available for the monitoring task
     */
    private final int NUMBER_OF_THREADS = 1;

    @Autowired
    private MonitoringConfig monitoringConfig;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(NUMBER_OF_THREADS);

    /**
     * Start monitoring a website
     *
     * @param url URL of the website to monitor
     */
    public synchronized void startMonitoring(String url) {
        log.info("Request to start monitoring " + url);
        monitoringData.addUrl(url);
    }

    public List<MonitoredUrl> getMonitoredSites() {
        return monitoringData.getMonitoredUrls();
    }

    public List<MonitoringResult> getMonitoringResults(final int id) {
        return monitoringData.getMonitoringResults(id);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        executorService.scheduleAtFixedRate(new ScheduledMonitoringJob(monitoringData),
                monitoringConfig.getInterval(),
                monitoringConfig.getInterval(), TimeUnit.SECONDS);
    }
}
