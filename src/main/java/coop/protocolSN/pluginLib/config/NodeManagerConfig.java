package coop.protocolSN.pluginLib.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "protocolsn.node-manager")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NodeManagerConfig {

    private String baseUri;
}
