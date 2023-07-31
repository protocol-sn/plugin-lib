package coop.protocolSN.pluginLib.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "protocolsn")
public class GlobalConfig {
}
