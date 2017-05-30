package de.gernd.simplemon.model;

import de.gernd.simplemon.model.entities.MonitoredResourceEntity;
import org.springframework.data.repository.CrudRepository;

public interface MonitoredEntityRepository extends CrudRepository<MonitoredResourceEntity, Long> {
}
