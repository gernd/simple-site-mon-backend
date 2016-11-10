package de.gernd.simplemon.service;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class MonitoredUrl {

    private String url;
    private int id;
}
