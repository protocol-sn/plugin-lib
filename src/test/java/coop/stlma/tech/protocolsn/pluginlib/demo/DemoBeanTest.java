package coop.stlma.tech.protocolsn.pluginlib.demo;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class DemoBeanTest {

    @Inject
    DemoBean demoBean;

    @Test
    void testHello() {
        String result = demoBean.hello();
        Assertions.assertEquals("Hello World!", result);
    }
}
