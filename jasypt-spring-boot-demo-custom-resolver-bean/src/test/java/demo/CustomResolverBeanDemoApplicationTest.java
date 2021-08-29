package demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootTest(classes = CustomResolverBeanDemoApplication.class)
public class CustomResolverBeanDemoApplicationTest {

	@Autowired
	ConfigurableEnvironment environment;

	@Autowired
	MyService service;

	static {
		System.setProperty("jasypt.encryptor.password", "password");
	}

	@Test
	public void testEnvironmentProperties() {
		Assertions.assertEquals("{cipherr}nrmZtkF7T0kjG/VodDvBw93Ct8EgjCA+", environment.getProperty("secret.property"));
		Assertions.assertEquals("chupacabras", environment.getProperty("secret2.property"));
	}

	@Test
	public void testServiceProperties() {
		Assertions.assertEquals("{cipherr}nrmZtkF7T0kjG/VodDvBw93Ct8EgjCA+", service.getSecret());
		Assertions.assertEquals("chupacabras", service.getSecret2());
	}

}
