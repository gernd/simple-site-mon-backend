package de.gernd.simplemon.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringResult {

    @Id
    @GeneratedValue
    private long id;

    private long timestamp;

    private long responseTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitoredResourceEntity_Id")
    private MonitoredResourceEntity monitoredResourceEntity;
}
