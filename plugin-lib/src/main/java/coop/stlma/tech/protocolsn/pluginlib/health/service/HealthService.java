package coop.stlma.tech.protocolsn.pluginlib.health.service;

import coop.stlma.tech.protocolsn.health.model.HealthResponse;
import coop.stlma.tech.protocolsn.health.model.HealthStatus;
import org.reactivestreams.Publisher;

/**
 * Service to indicate the current health of the plugin.
 *
 * @author John Meyerin
 */
public interface HealthService {

    /**
     * Get the current health of the plugin
     * @return  The current health of the plugin
     */
    Publisher<HealthResponse> getPluginHealth();

    /**
     * Set the current health of the plugin. Convenience method of @link #setHealthStatus(HealthStatus, String)
     * @param healthStatus  The current health of the plugin
     * @return              empty
     */
    Publisher<Void> setHealthStatus(HealthStatus healthStatus);

    /**
     * Set the health status of the plugin with description
     * @param healthStatus  The current health of the plugin
     * @param description   Human-readable description of health
     * @return              empty
     */
    Publisher<Void> setHealthStatus(HealthStatus healthStatus, String description);
}
