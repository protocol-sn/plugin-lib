package coop.stlma.tech.protocolsn.pluginlib.health.service.pojo;

import coop.stlma.tech.protocolsn.health.model.HealthStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain object representing the health status of a plugin
 *
 * @author John Meyerin
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@Serdeable
public class PluginHealth {

    /**
     * Category of health status
     */
    private HealthStatus healthStatus;

    /**
     * Human-readable description of health
     */
    private String description;
}
