package coop.stlma.tech.protocolsn.pluginlib.registration.service;

import coop.stlma.tech.protocolsn.pluginlib.registration.domain.PluginRegistration;
import reactor.core.publisher.Mono;

/**
 * Used to registre a plugin with the node manager
 *
 * @author John Meyerin
 */
public interface RegistrationService {

    Mono<PluginRegistration> register(PluginRegistration pluginRegistration);
}
