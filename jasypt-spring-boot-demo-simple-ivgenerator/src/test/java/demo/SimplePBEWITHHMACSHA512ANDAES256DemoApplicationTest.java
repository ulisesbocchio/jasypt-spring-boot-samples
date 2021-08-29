package demo;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest(classes = SimplePBEWITHHMACSHA512ANDAES_256DemoApplication.class)
public class SimplePBEWITHHMACSHA512ANDAES256DemoApplicationTest {

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
		Assertions.assertEquals("chupacabras", environment.getProperty("secret.property"));
		Assertions.assertEquals("chupacabras", environment.getProperty("secret2.property"));
	}

	@Test
	public void testServiceProperties() {
		Assertions.assertEquals("chupacabras", service.getSecret());
		Assertions.assertEquals("chupacabras", service.getSecret2());
	}

	@Test
	public void encryptProperty() {
		String message = "chupacabras";
		String encrypted = encryptorBean.encrypt(message);
		System.out.printf("Encrypted message %s\n", encrypted);
		String decrypted = encryptorBean.decrypt(encrypted);
		Assertions.assertEquals(message, decrypted);
		System.out.println();
	}

}
