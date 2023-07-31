package coop.protocolSN.pluginLib.mock;

import coop.protocolSN.pluginLib.autoregistration.domain.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NodeManagerControllerMock {

    public static RegistrationRequest receivedRequest;

    @PostMapping("/register")
    public ResponseEntity<Void> mockRegister(RegistrationRequest request) {
        receivedRequest = request;
        return ResponseEntity.ok().build();
    }
}
