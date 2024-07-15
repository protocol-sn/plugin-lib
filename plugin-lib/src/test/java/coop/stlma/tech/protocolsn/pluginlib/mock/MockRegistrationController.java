package coop.stlma.tech.protocolsn.pluginlib.mock;

import coop.stlma.tech.protocolsn.registration.model.PluginRegistration;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import java.util.UUID;

@Controller
@Requires(property = "registration.enabled", value = "true", defaultValue = "false")
public class MockRegistrationController {

    public MockRegistrationController() {
        System.out.println("MockRegistrationController created");
    }


    @Post("/plugin-registration")
    public HttpResponse<PluginRegistration> registerPlugin(@Body PluginRegistration pluginRegistration) {
        if (pluginRegistration.getPluginName().equals("fail")) {
            return HttpResponse.badRequest();
        }
        return HttpResponse.ok(new PluginRegistration(UUID.nameUUIDFromBytes("ok".getBytes()), "ok",
                "localhost:8081", null, null, null,
                null, null));
    }
}
