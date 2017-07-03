package de.gernd.simplemon.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MonitoredResourceEntity {
    @Id
    @GeneratedValue
    private long id;

    private String url;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "monitoredResourceEntity")
    private List<MonitoringResult> monitoringResults;

    public void addMonitoringResult(MonitoringResult monitoringResult) {
        monitoringResult.setMonitoredResourceEntity(this);
        monitoringResults.add(monitoringResult);
    }

    @Override
    public String toString(){
        return "Monitored Resource Entity with URL " + url + " and id " + id;
    }
}
