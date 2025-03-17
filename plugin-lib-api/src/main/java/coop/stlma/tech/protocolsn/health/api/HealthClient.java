package coop.stlma.tech.protocolsn.health.api;

import coop.stlma.tech.protocolsn.health.model.HealthResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;

/**
 * Client for getting health information of a registered plugin
 *
 * @author John Meyerin
 */
@Client("general::health")
public interface HealthClient extends HealthOperations {

    /**
     * Check the health of the plugin
     * @return  The health of the plugin
     */
    @Get("${" + HEALTH_CONFIG +":" + HealthOperations.HEALTH_ENDPOINT + "}")
    Publisher<HttpResponse<HealthResponse>> healthCheck();
}
