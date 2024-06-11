package coop.stlma.tech.protocolsn.pluginlib.registration.service;

import coop.stlma.tech.protocolsn.pluginlib.registration.domain.PluginRegistration;
import reactor.core.publisher.Mono;

/**
 * Used to register a plugin with the node manager
 *
 * @author John Meyerin
 */
public interface RegistrationService {

    /**
     * Begin the process of registering this plugin with node manager
     * @param pluginRegistration plugin registration info
     * @return The registered plugin
     */
    Mono<PluginRegistration> register(PluginRegistration pluginRegistration);
}
