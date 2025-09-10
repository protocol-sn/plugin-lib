package coop.stlma.tech.protocolsn.pluginlib.registration.service;

import coop.stlma.tech.protocolsn.nodemanager.PluginRegistration;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@MicronautTest
class RegistrationServiceImplTest {

    @Inject
    RegistrationServiceImpl registrationService;

    @Test
    void testRegister_happyPath() {

        PluginRegistration pluginRegistration = PluginRegistration.newBuilder()
                .setId(UUID.nameUUIDFromBytes("test".getBytes()).toString())
                .setPluginName("test")
                .setPluginLocation("localhost")
                .setPluginGrpcPort(8081)
                .build();
        PluginRegistration result = registrationService.register(pluginRegistration).block();
        Assertions.assertEquals("test", result.getPluginName());
        Assertions.assertEquals("localhost", result.getPluginLocation());
        Assertions.assertEquals(8081, result.getPluginGrpcPort());
    }
}
