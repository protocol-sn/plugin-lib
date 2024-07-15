package coop.stlma.tech.protocolsn.health.api;

import coop.stlma.tech.protocolsn.health.model.HealthResponse;
import io.micronaut.http.HttpResponse;
import org.reactivestreams.Publisher;

public interface HealthOperations {

    String HEALTH_CONFIG = "coop.stlma.tech.protocolsn.health.url";
    String HEALTH_ENDPOINT = "/general/v0.1.0/health";
    String HEALTH_ROLES = "node-manager";
    Publisher<HttpResponse<HealthResponse>> healthCheck();
}
