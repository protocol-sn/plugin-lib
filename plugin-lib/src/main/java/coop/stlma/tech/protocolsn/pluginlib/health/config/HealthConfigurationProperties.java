package coop.stlma.tech.protocolsn.pluginlib.health.config;

import coop.stlma.tech.protocolsn.health.api.HealthOperations;
import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@ConfigurationProperties("coop.stlma.tech.protocolsn.health-check")
public class HealthConfigurationProperties {

    private String url = HealthOperations.HEALTH_ENDPOINT;
    private boolean enabled = true;
}
