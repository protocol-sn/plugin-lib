package coop.stlma.tech.protocolsn.health.model;

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
public class HealthResponse {

    private HealthStatus healthStatus;
    private String description;
}
