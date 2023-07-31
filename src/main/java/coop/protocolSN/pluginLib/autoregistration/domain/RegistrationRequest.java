package coop.protocolSN.pluginLib.autoregistration.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RegistrationRequest {
    private String pluginId;
    private String pluginName;
    private String pluginSecret;
    private List<String> pluginRoles;
}
