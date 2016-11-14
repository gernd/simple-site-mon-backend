package de.gernd.simplemon.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="monitoring_config")
@Component
@Getter
@ToString
@Setter
public class MonitoringConfig {

    private Integer interval;
}
