package coop.protocolSN.pluginLib.autoregistration.listener;

import coop.protocolSN.pluginLib.autoregistration.domain.RegistrationRequest;
import coop.protocolSN.pluginLib.autoregistration.exception.RegistrationFailureException;
import coop.protocolSN.pluginLib.config.NodeManagerConfig;
import coop.protocolSN.pluginLib.config.PluginConfig;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Configuration
public class AutoregistrationStartupListener implements ApplicationListener<ContextStartedEvent> {

    private final WebClient webClient;
    private final NodeManagerConfig nodeManagerConfig;
    private final PluginConfig pluginConfig;

    public AutoregistrationStartupListener(WebClient webClient,
                                           NodeManagerConfig nodeManagerConfig,
                                           PluginConfig pluginConfig) {
        this.webClient = webClient;
        this.nodeManagerConfig = nodeManagerConfig;
        this.pluginConfig = pluginConfig;
    }

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {

        ResponseEntity response = webClient
                .post()
                .uri(nodeManagerConfig.getBaseUri() + "/register")
                .bodyValue(new RegistrationRequest(pluginConfig.getId(), pluginConfig.getName(),
                        pluginConfig.getSecret(), pluginConfig.getRoles()))
                .retrieve()
                .toBodilessEntity()
                .doOnError(throwable -> {
                    throw new RegistrationFailureException("Failed to reach node manager: " + throwable.getMessage());
                })
                .block();

        if (response.getStatusCode().isError()) {
            throw new RegistrationFailureException("Response code " + response.getStatusCode() + "received from node manager");
        }

    }
}
