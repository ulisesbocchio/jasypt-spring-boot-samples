package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CustomEncryptorDemoApplication.class)
public class CustomEncryptorDemoApplicationTest {

	@Autowired
	ConfigurableEnvironment environment;

	@Autowired
	EncryptablePropertyResolver resolver;

	static {
		System.setProperty("jasypt.encryptor.password", "password");
	}

	@Test
	public void testEnvironmentProperties() {
		assertEquals("chupacabras", environment.getProperty("secret.property"));
	}
}
