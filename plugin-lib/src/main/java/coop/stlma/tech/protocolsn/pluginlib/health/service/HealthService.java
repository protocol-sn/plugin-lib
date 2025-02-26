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

    Publisher<HealthResponse> getPluginHealth();

    Publisher<Void> setHealthStatus(HealthStatus healthStatus);
    Publisher<Void> setHealthStatus(HealthStatus healthStatus, String description);
}
