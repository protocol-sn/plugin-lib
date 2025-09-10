package coop.stlma.tech.protocolsn.pluginlib.event;

import coop.stlma.tech.protocolsn.nodemanager.PluginRegistration;
import coop.stlma.tech.protocolsn.pluginlib.registration.event.RegistrationStartupListener;
import coop.stlma.tech.protocolsn.pluginlib.registration.service.RegistrationService;
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
                null);

        Mockito.when(registrationServiceMock.register(registrationArgumentCaptor.capture()))
                .thenReturn(Mono.just(PluginRegistration.newBuilder().build()));

        registrationStartupListener.onApplicationEvent(null);

        PluginRegistration result = registrationArgumentCaptor.getValue();

        Assertions.assertEquals(8080, result.getPluginGrpcPort());

    }
}
