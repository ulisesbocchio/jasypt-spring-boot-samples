package demo;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SimplePBEWITHHMACSHA512ANDAES_256MellowareDemoApplication.class)
public class SimplePBEWITHHMACSHA512ANDAES256MellowareDemoApplicationTest {

	@Autowired
	Environment environment;

	@Autowired
	MyService service;

	@Autowired
	StringEncryptor encryptorBean;

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

	@Test
	public void encryptProperty() {
		String message = "chupacabras";
		String encrypted = encryptorBean.encrypt(message);
		System.out.printf("Encrypted melloware message %s\n", encrypted);
		String decrypted = encryptorBean.decrypt(encrypted);
		Assert.assertEquals(message, decrypted);
		System.out.println();
	}

}
