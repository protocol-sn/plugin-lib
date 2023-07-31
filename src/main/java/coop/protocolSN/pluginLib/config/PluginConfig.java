package coop.protocolSN.pluginLib.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "protocolsn.plugin")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PluginConfig {
    private String id;
    private String secret;
    private List<String> roles;
    private String name;
}
