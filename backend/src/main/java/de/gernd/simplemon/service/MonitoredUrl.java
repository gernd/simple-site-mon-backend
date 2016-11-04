package de.gernd.simplemon.service;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MonitoredUrl {

    private String url;
    private int id;
}
