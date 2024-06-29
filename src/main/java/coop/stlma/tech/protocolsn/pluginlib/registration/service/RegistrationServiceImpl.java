package coop.stlma.tech.protocolsn.pluginlib.registration.service;

import coop.stlma.tech.protocolsn.pluginlib.registration.error.PluginRegistrationException;
import coop.stlma.tech.protocolsn.registration.api.RegistrationClient;
import coop.stlma.tech.protocolsn.registration.model.PluginRegistration;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Default implementation of registration service
 * Registers a plugin with the node manager
 *
 * Reads from property `coop.stlma.tech.protocolsn.node-manager.registration.url` as the endpoint
 * Uses the property `micronaut.http.client.node-manager.url` as the node manager host
 *
 * @author John Meyerin
 */
@Singleton
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationClient registrationClient;
    private final String registrationUrl;

    public RegistrationServiceImpl(RegistrationClient registrationClient,
                                   @Value("${coop.stlma.tech.protocolsn.node-manager.registration.url:/plugin-registration}") String registration) {
        this.registrationClient = registrationClient;
        log.debug("Setting up registration at {}", registration);
        this.registrationUrl = registration;
    }

    /**
     * Registers a plugin with the node manager
     * @param pluginRegistration plugin registration info
     * @return Plugin registration as received by node manager
     */
    @Override
    public Mono<PluginRegistration> register(PluginRegistration pluginRegistration) {
        log.debug("Registering plugin {}", pluginRegistration.getPluginName());
        HttpRequest<PluginRegistration> request = HttpRequest.POST(registrationUrl, pluginRegistration);
        return Mono.from(registrationClient.registerPlugin(pluginRegistration))
                .map(response -> response.getBody(PluginRegistration.class))
                .map(body -> body.orElseThrow(() -> new PluginRegistrationException("Failed to register plugin.")));
    }
}
