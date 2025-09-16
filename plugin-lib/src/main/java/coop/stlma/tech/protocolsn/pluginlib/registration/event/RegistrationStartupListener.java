package coop.stlma.tech.protocolsn.pluginlib.registration.event;

import coop.stlma.tech.protocolsn.nodemanager.PluginRegistration;
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
@Requires(property = "coop.stlma.tech.protocolsn.registration.on-startup", value = "true", defaultValue = "true", notEnv = "test")
@Slf4j
public class RegistrationStartupListener implements ApplicationEventListener<ApplicationStartupEvent> {

    private final RegistrationService registrationService;
    private final String pluginName;
    private final String pluginHost;
    private final Integer pluginGrpcPort;
    private final String pluginTarget;

    public RegistrationStartupListener(RegistrationService registrationService,
                                       @Value("${coop.stlma.tech.protocolsn.plugin-name:}") String pluginName,
                                       @Value("${coop.stlma.tech.protocolsn.plugin-host:}") String pluginHost,
                                       @Value("${coop.stlma.tech.protocolsn.plugin-grpc-port:}") String pluginGrpcPort,
                                       @Value("${coop.stlma.tech.protocolsn.plugin-target:}") String pluginTarget) {
        this.registrationService = registrationService;
        this.pluginName = pluginName;
        this.pluginHost = StringUtils.isNotEmpty(pluginHost) ? pluginHost : null;
        this.pluginGrpcPort = StringUtils.isDigits(pluginGrpcPort) ? Integer.parseInt(pluginGrpcPort) : null;
        this.pluginTarget = StringUtils.isNotEmpty(pluginTarget) ? pluginTarget : null;
    }

    /**
     * Register a plugin on startup
     * @param event Startup event
     */
    @Override
    public void onApplicationEvent(ApplicationStartupEvent event) {
        log.debug("Registering plugin {} on {} and port {}", pluginName, pluginHost, pluginGrpcPort);

        PluginRegistration.Builder builder = PluginRegistration.newBuilder()
                        .setPluginName(pluginName);

        if (StringUtils.isNotEmpty(pluginTarget)) {
            builder.setPluginTarget(pluginTarget);
        }
        else {
            builder.setPluginLocation(pluginHost);
            builder.setPluginGrpcPort(pluginGrpcPort);
        }
        registrationService.register(builder.build())
                .block();
    }
}
