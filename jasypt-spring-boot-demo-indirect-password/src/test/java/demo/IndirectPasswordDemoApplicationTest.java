package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Objects;

@SpringBootTest(classes = IndirectPasswordDemoApplication.class)
public class IndirectPasswordDemoApplicationTest {

    @Autowired
    ConfigurableEnvironment environment;

    @Autowired
    MyService service;

    @Autowired
    EncryptablePropertyResolver resolver;

    @Autowired
    StringEncryptor encryptor;

    static {
        System.setProperty("jasypt.encryptor.password", "password");
		System.setProperty("ENCRYPTED_PASSWORD", "9ah+QnEdccHCkARkGZ7f0v5BLXXC+z0mr4hyjgE8T2G7mF75OBU1DgmC0YsGis8x");
    }

    @Test
    public void testStringEncryptorIsPresent() {
        Assertions.assertNotNull(encryptor, "StringEncryptor should be present");
    }

    @Test
    public void testEnvironmentProperties() {
        Assertions.assertEquals("chupacabras", environment.getProperty("secret.property"));
        Assertions.assertEquals("chupacabras", environment.getProperty("secret2.property"));
    }

	@Test
	public void testIndirectPropertiesDirectly() {
		Assertions.assertEquals("chupacabras", environment.getProperty("indirect.secret.property"));
		Assertions.assertEquals("chupacabras", environment.getProperty("indirect.secret.property2"));
		Assertions.assertEquals("https://uli:chupacabras@localhost:30000", environment.getProperty("endpoint"));
	}

    @Test
    public void testServiceProperties() {
        Assertions.assertEquals("chupacabras", service.getSecret());
        Assertions.assertEquals("chupacabras", service.getSecret2());
    }

    @Test
    public void testSkipRandomPropertySource() {
        Assertions.assertEquals(Objects.requireNonNull(environment.getPropertySources().get("random")).getClass(), RandomValuePropertySource.class);
    }

}
