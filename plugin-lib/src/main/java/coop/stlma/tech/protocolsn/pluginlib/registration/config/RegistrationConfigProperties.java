package coop.stlma.tech.protocolsn.pluginlib.registration.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("coop.stlma.tech.protocolsn.node-manager.registration")
public class RegistrationConfigProperties {

    private String url = "/plugin-registration";
}
