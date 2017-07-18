package demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CustomResolverBeanDemoApplication.class)
public class CustomResolverBeanDemoApplicationTests {

	@Autowired
	ConfigurableEnvironment environment;

	@Autowired
	MyService service;

	static {
		System.setProperty("jasypt.encryptor.password", "password");
	}

	@Test
	public void testEnvironmentProperties() {
		Assert.assertEquals("chupacabras", environment.getProperty("secret.property"));
		Assert.assertEquals("chupacabras", environment.getProperty("secret2.property"));
	}

	@Test
	public void testServiceProperties() {
		Assert.assertEquals("chupacabras", service.getSecret());
		Assert.assertEquals("chupacabras", service.getSecret2());
	}

}
