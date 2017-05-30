package de.gernd.simplemon.endpoints.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class MonitoredUrl {

    private String url;
    private long id;
}