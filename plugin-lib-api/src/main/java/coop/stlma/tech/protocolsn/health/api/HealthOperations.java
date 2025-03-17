package coop.stlma.tech.protocolsn.health.api;

import coop.stlma.tech.protocolsn.health.model.HealthResponse;
import io.micronaut.http.HttpResponse;
import org.reactivestreams.Publisher;

/**
 * Health check operations
 *
 * @author John Meyerin
 */
public interface HealthOperations {

    /**
     * Get this plugin's current health
     *
     * @return the current health
     */
    String HEALTH_CONFIG = "coop.stlma.tech.protocolsn.health.url";
    String HEALTH_ENDPOINT = "/general/v0.1.0/health";
    String HEALTH_ROLES = "node-manager";
    Publisher<HttpResponse<HealthResponse>> healthCheck();
}
