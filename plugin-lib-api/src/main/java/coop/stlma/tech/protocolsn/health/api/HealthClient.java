package coop.stlma.tech.protocolsn.health.api;

import coop.stlma.tech.protocolsn.health.model.HealthResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;

@Client("general::health")
public interface HealthClient extends HealthOperations {

    @Get("${" + HEALTH_CONFIG +":" + HealthOperations.HEALTH_ENDPOINT + "}")
    Publisher<HttpResponse<HealthResponse>> healthCheck();
}
