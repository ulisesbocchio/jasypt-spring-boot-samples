package demo;

import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EncryptableEnvironmentContextLoader extends SpringBootContextLoader {
    @Override
    protected ConfigurableEnvironment getEnvironment() {
        String password = System.getProperty("jasypt.encryptor.password");
        org.springframework.util.Assert.notNull(password, "Encryption password must be provided!");
        return StandardEncryptableEnvironment.builder().resolver(new MyEncryptablePropertyResolver(password.toCharArray())).build();
    }
}

@ContextConfiguration(loader = EncryptableEnvironmentContextLoader.class)
@SpringBootTest(classes = CustomResolverDemoApplication.class)
public class CustomResolverDemoApplicationTest {

    @Autowired
    ConfigurableEnvironment environment;

    @Autowired
    MyService service;

    static {
        System.setProperty("jasypt.encryptor.password", "password");
    }

    @Test
    public void testEnvironmentProperties() {
        assertEquals("chupacabras", environment.getProperty("secret.property"));
        assertEquals("chupacabras", environment.getProperty("secret2.property"));
    }

    @Test
    public void testServiceProperties() {
        assertEquals("chupacabras", service.getSecret());
        assertEquals("chupacabras", service.getSecret2());
    }

}
