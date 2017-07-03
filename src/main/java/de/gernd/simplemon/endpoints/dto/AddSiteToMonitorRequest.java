package de.gernd.simplemon.endpoints.dto;

import lombok.Data;

/**
 * DTO used for adding a site to monitor
 */
@Data
public class AddSiteToMonitorRequest {
    private String url;
}
