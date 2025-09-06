package coop.stlma.tech.protocolsn.pluginlib.event;

import coop.stlma.tech.protocolsn.nodemanager.registration.model.PluginRegistration;
import coop.stlma.tech.protocolsn.pluginlib.health.config.HealthConfigurationProperties;
import coop.stlma.tech.protocolsn.pluginlib.registration.event.RegistrationStartupListener;
import coop.stlma.tech.protocolsn.pluginlib.registration.service.RegistrationService;
import io.micronaut.runtime.event.ApplicationStartupEvent;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

@MicronautTest
class RegistrationStartupListenerTest {

    RegistrationService registrationServiceMock = Mockito.mock(RegistrationService.class);

    ArgumentCaptor<PluginRegistration> registrationArgumentCaptor = ArgumentCaptor.forClass(PluginRegistration.class);

    @Test
    void testInit_happyPath() {
        RegistrationStartupListener registrationStartupListener = new RegistrationStartupListener(
                registrationServiceMock,
                "TestPlugin",
                "localhost",
                "8080",
                null,
                null);

        Mockito.when(registrationServiceMock.register(registrationArgumentCaptor.capture()))
                .thenReturn(Mono.just(new PluginRegistration(
                        null,
                        null,
                        null,
                        0,
                        null,
                        null,
                        null,
                        null,
                        null)));

        registrationStartupListener.onApplicationEvent(null);

        PluginRegistration result = registrationArgumentCaptor.getValue();

        Assertions.assertEquals(8080, result.getPluginGrpcPort());

    }
}
