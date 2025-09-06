package coop.stlma.tech.protocolsn.pluginlib.health.config;

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
     * Does this plugin support health checks?
     */
    private boolean enabled = true;
}
