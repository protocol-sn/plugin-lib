package coop.protocolSN.pluginLib.autoregistration.listener;

import coop.protocolSN.pluginLib.config.NodeManagerConfig;
import coop.protocolSN.pluginLib.security.config.WebClientConfig;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@ContextConfiguration(classes = {AutoregistrationStartupListener.class, WebClientConfig.class})
//@SpringBootConfiguration
public class AutoregistrationStartupListenerTest {

    @Value("${local.server.port:8888}")
    private int port;

//    @ClassRule
//    public static GenericContainer nodeManager = new GenericContainer("openjdk")
//            .withExposedPorts(8888);

//    @Autowired
//    private NodeManagerConfig nodeManagerConfig;

    @Autowired
    private AutoregistrationStartupListener autoregistrationStartupListener;

    @Autowired
    private ApplicationContext applicationContext;

//    @BeforeEach
//    public void setup() {
//        nodeManagerConfig.setBaseUri("http://localhost:" + port);
//    }

    @Test
    public void testContainer() {
        ContextStartedEvent event = new ContextStartedEvent(applicationContext);
        autoregistrationStartupListener.onApplicationEvent(event);
    }

    @SpringBootApplication
    static class TestConfiguration {

    }
}
