package demo;

import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ContextConfiguration;

class EncryptableEnvironmentContextLoader extends SpringBootContextLoader {
	@Override
	protected ConfigurableEnvironment getEnvironment() {
		return StandardEncryptableEnvironment.builder().detector(new MyEncryptablePropertyDetector()).build();
	}
}
@ContextConfiguration(loader = EncryptableEnvironmentContextLoader.class)
@SpringBootTest(classes = CustomDetectorDemoApplication.class)
public class CustomDetectorDemoApplicationTest {
	@Autowired
	ConfigurableEnvironment environment;

	@Autowired
	MyService service;

	static {
		System.setProperty("jasypt.encryptor.password", "password");
	}

	@Test
	public void testEnvironmentProperties() {
		Assertions.assertEquals("chupacabras", environment.getProperty("secret.property"));
		Assertions.assertEquals("chupacabras", environment.getProperty("secret2.property"));
	}

	@Test
	public void testServiceProperties() {
		Assertions.assertEquals("chupacabras", service.getSecret());
		Assertions.assertEquals("chupacabras", service.getSecret2());
	}

}
