package coop.stlma.tech.protocolsn.pluginlib.registration.event;

import coop.stlma.tech.protocolsn.pluginlib.registration.domain.PluginRegistration;
import coop.stlma.tech.protocolsn.pluginlib.registration.service.RegistrationService;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.core.util.StringUtils;
import io.micronaut.runtime.event.ApplicationStartupEvent;
import io.micronaut.runtime.server.EmbeddedServer;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

/**
 * Runs on startup to register the plugin with the node manager
 * To prevent this from happening on startup, set `coop.stlma.tech.protocolsn.registration.on-startup` to `false`
 *
 * @author John Meyerin
 */
@Singleton
@Requires(property = "coop.stlma.tech.protocolsn.registration.on-startup", value = "true", defaultValue = "true")
@Slf4j
public class RegistrationStartupListener implements ApplicationEventListener<ApplicationStartupEvent> {

    private final RegistrationService registrationService;
    private final String pluginName;
    private final String pluginHost;

    public RegistrationStartupListener(RegistrationService registrationService,
                                       @Value("${coop.stlma.tech.protocolsn.plugin-name}") String pluginName,
                                       @Value("${coop.stlma.tech.protocolsn.plugin-host:}") String pluginHost,
                                       EmbeddedServer embeddedServer) {
        this.registrationService = registrationService;
        this.pluginName = pluginName;
        this.pluginHost = StringUtils.isNotEmpty(pluginHost) ? pluginHost : String.valueOf(embeddedServer.getURL());
    }

    @Override
    public void onApplicationEvent(ApplicationStartupEvent event) {
        log.debug("Registering plugin {} on {}", pluginName, pluginHost);
        registrationService.register(new PluginRegistration(null, pluginName, pluginHost)).block();
    }
}
