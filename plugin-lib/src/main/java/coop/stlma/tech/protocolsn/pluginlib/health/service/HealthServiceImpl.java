package coop.stlma.tech.protocolsn.pluginlib.health.service;

import coop.stlma.tech.protocolsn.health.model.HealthResponse;
import coop.stlma.tech.protocolsn.health.model.HealthStatus;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

/**
 * Service to indicate the current health of the plugin.
 *
 * @author John Meyerin
 */
@Singleton
@Requires(property = "coop.stlma.tech.protocolsn.health-check.enabled", notEquals = StringUtils.FALSE, defaultValue = StringUtils.TRUE)
@Slf4j
public class HealthServiceImpl implements HealthService {

    private HealthStatus healthStatus = HealthStatus.HEALTHY;
    private String healthDescription;

    /**
     * Get the current health of the plugin
     * @return HealthResponse
     */
    @Override
    public Publisher<HealthResponse> getPluginHealth() {
        return Mono.just(new HealthResponse(healthStatus, healthDescription));
    }

    /**
     * Set the health status of the plugin with no description
     *
     * Convenience method for @link #setHealthStatus(HealthStatus, String)
     *
     * @param healthStatus health status
     */
    @Override
    public Publisher<Void> setHealthStatus(HealthStatus healthStatus) {
        return setHealthStatus(healthStatus, null);
    }

    /**
     * set the health status of the plugin with description
     * @param healthStatus  health status
     * @param description   human-readable description of health
     */
    @Override
    public Publisher<Void> setHealthStatus(HealthStatus healthStatus, String description) {
        if (healthStatus != HealthStatus.HEALTHY) {
            log.warn("Setting health status to {} {}", healthStatus, description);
        }
        else {
            log.debug("Setting health status to {} {}", healthStatus, description);
        }
        this.healthStatus = healthStatus;
        this.healthDescription = description;
        return Mono.empty();
    }
}
