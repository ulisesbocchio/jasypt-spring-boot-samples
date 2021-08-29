package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = PasswordGcmEncryptorDemoApplication.class, properties = {"spring.profiles.active=password"})
public class PasswordGcmEncryptorDemoApplicationTest {

    @Autowired
    ConfigurableEnvironment environment;

    @Autowired
    EncryptablePropertyResolver resolver;

    @Test
    public void testEnvironmentProperties() {
        assertEquals("This is the secret message... BOOHOOO!", environment.getProperty("secret.property"));
    }
}
