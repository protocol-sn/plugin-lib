package coop.stlma.tech.protocolsn.pluginlib.registration.service;

import coop.stlma.tech.protocolsn.pluginlib.registration.domain.PluginRegistration;
import coop.stlma.tech.protocolsn.pluginlib.registration.error.PluginRegistrationException;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
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

    private final HttpClient nodeManagerClient;
    private final String registrationUrl;

    public RegistrationServiceImpl(@Client("node-manager") HttpClient nodeManagerClient,
                                   @Value("${coop.stlma.tech.protocolsn.node-manager.registration.url:/plugin-registration}") String registration) {
        this.nodeManagerClient = nodeManagerClient;
        this.registrationUrl = registration;
    }

    /**
     * Registers a plugin with the node manager
     * @param pluginRegistration plugin registration info
     * @return Plugin registration as received by node manager
     */
    @Override
    public Mono<PluginRegistration> register(PluginRegistration pluginRegistration) {
        HttpRequest<PluginRegistration> request = HttpRequest.POST(registrationUrl, pluginRegistration);
        return Mono.from(nodeManagerClient.exchange(request, PluginRegistration.class))
                .map(response -> response.getBody(PluginRegistration.class)
                        .orElseThrow(() -> new PluginRegistrationException("Failed to register plugin.")));
    }
}
