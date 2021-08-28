package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = LocationGcmEncryptorDemoApplication.class, properties = {"spring.profiles.active=location"})
public class LocationGcmEncryptorDemoApplicationTest {

    @Autowired
    ConfigurableEnvironment environment;

    @Autowired
    EncryptablePropertyResolver resolver;

    @Test
    public void testEnvironmentProperties() {
        assertEquals("This is the secret message... BOOHOOO!", environment.getProperty("secret.property"));
    }
}
