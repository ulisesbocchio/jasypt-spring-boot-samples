package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import com.ulisesbocchio.jasyptspringboot.environment.EncryptableEnvironment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SimpleDemoApplication.class)
public class SimpleDemoApplicationTest {

	@Autowired
	ConfigurableEnvironment environment;

	@Autowired
	MyService service;

	@Autowired
	EncryptablePropertyResolver resolver;

	static {
		System.setProperty("jasypt.encryptor.password", "password");
	}

	@Test
	public void testEnvironmentProperties() {
		Assert.assertEquals("chupacabras", environment.getProperty("secret.property"));
		Assert.assertEquals("chupacabras", environment.getProperty("secret2.property"));
	}

@Test
	public void testIndirectProperties() {
	EncryptableEnvironment encryptableEnvironment = new EncryptableEnvironment(environment, resolver);
	Assert.assertEquals("chupacabras", encryptableEnvironment.getProperty("indirect.secret.property"));
	Assert.assertEquals("https://uli:chupacabras@localhost:30000", encryptableEnvironment.getProperty("endpoint"));
	}

	@Test
	public void testServiceProperties() {
		Assert.assertEquals("chupacabras", service.getSecret());
		Assert.assertEquals("chupacabras", service.getSecret2());
	}

}
