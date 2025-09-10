package coop.stlma.tech.protocolsn.pluginlib.health.endpoint;

import coop.stlma.tech.protocolsn.health.model.HealthStatus;
import coop.stlma.tech.protocolsn.pluginlib.Empty;
import coop.stlma.tech.protocolsn.pluginlib.HealthGrpc;
import coop.stlma.tech.protocolsn.pluginlib.HealthResponse;
import coop.stlma.tech.protocolsn.pluginlib.health.service.HealthService;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * gRPC endpoint for health checks
 *
 * @author John Meyerin
 */
@Singleton
@Slf4j
public class HealthEndpoint extends HealthGrpc.HealthImplBase {
    private final HealthService healthService;

    public HealthEndpoint(HealthService healthService) {
        this.healthService = healthService;
    }

    @Override
    public void healthCheck(Empty request, StreamObserver<HealthResponse> responseObserver) {
        log.debug("Receiving health check request");
        responseObserver.onNext(Mono.from(healthService.getPluginHealth())
                .map(pluginHealth -> HealthResponse.newBuilder()
                        .setHealthStatus(Optional.ofNullable(pluginHealth.getHealthStatus()).orElse(HealthStatus.HEALTHY).name())
                        .setDescription(Optional.ofNullable(pluginHealth.getDescription()).orElse(""))
                        .build()).block());
        responseObserver.onCompleted();
    }
}
