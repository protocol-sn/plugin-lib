package coop.stlma.tech.protocolsn.pluginlib.registration.service;

import coop.stlma.tech.protocolsn.registration.model.PluginRegistration;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

@MicronautTest
@Disabled
class RegistrationServiceImplTest implements TestPropertyProvider {

    @Inject
    RegistrationServiceImpl registrationService;


    @Test
    void testRegister_happyPath() {

        PluginRegistration pluginRegistration = new PluginRegistration(UUID.nameUUIDFromBytes("test".getBytes()),
                "test", "localhost:8081", null, null,
                null, null, null);
        PluginRegistration result = registrationService.register(pluginRegistration).block();
        Assertions.assertEquals("test", result.getPluginName());
        Assertions.assertEquals("localhost:8081", result.getPluginLocation());
    }

    @Override
    public @NonNull Map<String, String> getProperties() {
        return Map.of("coop.stlma.tech.protocolsn.node-manager.registration.url", "/plugin-registration");
    }
}
