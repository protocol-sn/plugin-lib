package coop.stlma.tech.protocolsn.pluginlib.health.config;

import coop.stlma.tech.protocolsn.health.api.HealthOperations;
import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Properties related to the health checks for the plugin
 *
 * @author John Meyerin
 */
@Setter
@Getter
@NoArgsConstructor
@ConfigurationProperties("coop.stlma.tech.protocolsn.health-check")
public class HealthConfigurationProperties {

    /**
     * The endpoint at which the health check can be found
     */
    private String endpoint = HealthOperations.HEALTH_ENDPOINT;

    /**
     * Does this plugin support health checks?
     */
    private boolean enabled = true;
}
