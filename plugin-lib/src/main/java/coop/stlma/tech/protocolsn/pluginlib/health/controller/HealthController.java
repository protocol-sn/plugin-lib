package coop.stlma.tech.protocolsn.pluginlib.health.controller;

import coop.stlma.tech.protocolsn.health.api.HealthOperations;
import coop.stlma.tech.protocolsn.health.model.HealthResponse;
import coop.stlma.tech.protocolsn.pluginlib.health.service.HealthService;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Controller
@Requires(bean = HealthService.class)
public class HealthController implements HealthOperations {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @Get("${" + HEALTH_CONFIG +":" + HealthOperations.HEALTH_ENDPOINT + "}")
    @RolesAllowed(HEALTH_ROLES)
    @SecurityRequirement(name = "pluginClient")
    @Override
    public Publisher<HttpResponse<HealthResponse>> healthCheck() {
        return Mono.from(healthService.getPluginHealth())
                .map(HttpResponse::ok);
    }
}
