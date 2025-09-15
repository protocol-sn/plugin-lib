package coop.stlma.tech.protocolsn.pluginlib.health.endpoint;

import coop.stlma.tech.protocolsn.health.api.HealthClient;
import coop.stlma.tech.protocolsn.pluginlib.HealthResponse;
import coop.stlma.tech.protocolsn.pluginlib.health.service.HealthService;
import coop.stlma.tech.protocolsn.health.model.HealthStatus;
import coop.stlma.tech.protocolsn.pluginlib.health.service.pojo.PluginHealth;
import io.grpc.ManagedChannel;
import io.micronaut.context.annotation.Primary;
import io.micronaut.grpc.annotation.GrpcChannel;
import io.micronaut.grpc.server.GrpcServerChannel;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

import java.util.concurrent.Executor;

@MicronautTest
class HealthEndpointTest {
    @GrpcChannel(GrpcServerChannel.NAME)
    @Inject
    ManagedChannel channel;

    @Inject
    @Named(TaskExecutors.BLOCKING)
    Executor blockingExecutor;

    HealthClient healthClient;

    @MockBean
    @Primary
    HealthService healthServiceMock = Mockito.mock(HealthService.class);

    @BeforeEach
    void setup() {
        healthClient = HealthClient.create(channel, blockingExecutor);
    }

    @Test
    void testHealthCheck_happyPath() {
        PluginHealth pluginHealth = new PluginHealth();
        pluginHealth.setHealthStatus(HealthStatus.HEALTHY);
        pluginHealth.setDescription("happy mock plugin");

        Mockito.when(healthServiceMock.getPluginHealth()).thenReturn(Mono.just(pluginHealth));
        HealthResponse response = healthClient.healthCheckBlocking();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HealthStatus.HEALTHY.toString(), response.getHealthStatus());
        Assertions.assertEquals("happy mock plugin", response.getDescription());
    }

    @Test
    @Disabled("Enable when error handling implemented")
    void testHealthCheck_serviceError() {

        Mockito.when(healthServiceMock.getPluginHealth()).thenReturn(Mono.error(new RuntimeException("mock error")));

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> healthClient.healthCheckBlocking());
        Assertions.assertEquals("INTERNAL: mock error", thrown.getMessage());
    }
}
