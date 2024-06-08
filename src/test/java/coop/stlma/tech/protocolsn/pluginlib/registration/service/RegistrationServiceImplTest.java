package coop.stlma.tech.protocolsn.pluginlib.registration.service;

import coop.stlma.tech.protocolsn.pluginlib.registration.domain.PluginRegistration;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.netty.DefaultHttpClient;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@MicronautTest
class RegistrationServiceImplTest implements TestPropertyProvider {

    @Inject
    RegistrationServiceImpl registrationService;

    @MockBean
    @Replaces(DefaultHttpClient.class)
    HttpClient mockClient = Mockito.mock(HttpClient.class);


    @Test
    void testRegister_happyPath() {
        Mockito.when(mockClient.exchange(Mockito.any(HttpRequest.class), Mockito.eq(PluginRegistration.class)))
                .thenReturn(Mono.just(HttpResponse.ok(
                        new PluginRegistration(UUID.nameUUIDFromBytes("test".getBytes()),
                                "test",
                                "localhost:8081"))));

        PluginRegistration pluginRegistration = new PluginRegistration(UUID.nameUUIDFromBytes("test".getBytes()), "test", "localhost:8081");
        PluginRegistration result = registrationService.register(pluginRegistration).block();
        Assertions.assertEquals("test", result.getPluginName());
        Assertions.assertEquals("localhost:8081", result.getPluginLocation());
    }

    @Override
    public @NonNull Map<String, String> getProperties() {
        return Map.of("coop.stlma.tech.protocolsn.node-manager.registration.url", "/plugin-registration");
    }
}
