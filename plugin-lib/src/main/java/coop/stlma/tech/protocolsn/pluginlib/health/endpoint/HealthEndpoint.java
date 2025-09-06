package coop.stlma.tech.protocolsn.pluginlib.health.endpoint;

import coop.stlma.tech.protocolsn.health.model.HealthStatus;
import coop.stlma.tech.protocolsn.pluginlib.Empty;
import coop.stlma.tech.protocolsn.pluginlib.HealthGrpc;
import coop.stlma.tech.protocolsn.pluginlib.HealthResponse;
//import coop.stlma.tech.protocolsn.pluginlib.ReactorHealthGrpc;
import coop.stlma.tech.protocolsn.pluginlib.health.service.HealthService;
import coop.stlma.tech.protocolsn.pluginlib.health.service.pojo.PluginHealth;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
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
        healthService.getPluginHealth()
                .subscribe(new Subscriber<>() {
                    Subscription subscription;
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        subscription.request(1);
                    }

                    @Override
                    public void onNext(PluginHealth pluginHealth) {
                        log.trace("Preparing health response of {} - {}", pluginHealth.getHealthStatus(), pluginHealth.getDescription());
                        responseObserver.onNext(HealthResponse.newBuilder()
                                .setHealthStatus(String.valueOf(pluginHealth.getHealthStatus()))
                                .setDescription(pluginHealth.getDescription())
                                .build());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        log.error("Error in health endpoint", throwable);
                        responseObserver.onError(Status.INTERNAL.withDescription(throwable.getMessage()).asRuntimeException());
                    }

                    @Override
                    public void onComplete() {
                        log.trace("Completed sub. Returning health response");
                        responseObserver.onCompleted();
                    }
                });
    }
}
