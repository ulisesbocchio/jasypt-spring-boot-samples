package demo;

import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricConfig;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricStringEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.ulisesbocchio.jasyptspringboot.util.AsymmetricCryptography.KeyFormat.PEM;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SimpleAsymmetricDemoApplication.class)
public class SimpleAsymmetricDemoApplicationCamelTest {

	@Autowired
	Environment environment;

	@Autowired
	MyService service;

	static {
		System.setProperty("jasypt.encryptor.private-key-string", "-----BEGIN PRIVATE KEY-----\n" +
				"MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCtB/IYK8E52CYM\n" +
				"ZTpyIY9U0HqMewyKnRvSo6s+9VNIn/HSh9+MoBGiADa2MaPKvetS3CD3CgwGq/+L\n" +
				"IQ1HQYGchRrSORizOcIp7KBx+Wc1riatV/tcpcuFLC1j6QJ7d2I+T7RA98Sx8X39\n" +
				"orqlYFQVysTw/aTawX/yajx0UlTW3rNAY+ykeQ0CBHowtTxKM9nGcxLoQbvbYx1i\n" +
				"G9JgAqye7TYejOpviOH+BpD8To2S8zcOSojIhixEfayay0gURv0IKJN2LP86wkpA\n" +
				"uAbL+mohUq1qLeWdTEBrIRXjlnrWs1M66w0l/6JwaFnGOqEB6haMzE4JWZULYYpr\n" +
				"2yKyoGCRAgMBAAECggEAQxURhs1v3D0wgx27ywO3zeoFmPEbq6G9Z6yMd5wk7cMU\n" +
				"vcpvoNVuAKCUlY4pMjDvSvCM1znN78g/CnGF9FoxJb106Iu6R8HcxOQ4T/ehS+54\n" +
				"kDvL999PSBIYhuOPUs62B/Jer9FfMJ2veuXb9sGh19EFCWlMwILEV/dX+MDyo1qQ\n" +
				"aNzbzyyyaXP8XDBRDsvPL6fPxL4r6YHywfcPdBfTc71/cEPksG8ts6um8uAVYbLI\n" +
				"DYcsWopjVZY/nUwsz49xBCyRcyPnlEUJedyF8HANfVEO2zlSyRshn/F+rrjD6aKB\n" +
				"V/yVWfTEyTSxZrBPl4I4Tv89EG5CwuuGaSagxfQpAQKBgQDXEe7FqXSaGk9xzuPa\n" +
				"zXy8okCX5pT6545EmqTP7/JtkMSBHh/xw8GPp+JfrEJEAJJl/ISbdsOAbU+9KAXu\n" +
				"PmkicFKbodBtBa46wprGBQ8XkR4JQoBFj1SJf7Gj9ozmDycozO2Oy8a1QXKhHUPk\n" +
				"bPQ0+w3efwoYdfE67ZodpFNhswKBgQDN9eaYrEL7YyD7951WiK0joq0BVBLK3rwO\n" +
				"5+4g9IEEQjhP8jSo1DP+zS495t5ruuuuPsIeodA79jI8Ty+lpYqqCGJTE6muqLMJ\n" +
				"Diy7KlMpe0NZjXrdSh6edywSz3YMX1eAP5U31pLk0itMDTf2idGcZfrtxTLrpRff\n" +
				"umowdJ5qqwKBgF+XZ+JRHDN2aEM0atAQr1WEZGNfqG4Qx4o0lfaaNs1+H+knw5kI\n" +
				"ohrAyvwtK1LgUjGkWChlVCXb8CoqBODMupwFAqKL/IDImpUhc/t5uiiGZqxE85B3\n" +
				"UWK/7+vppNyIdaZL13a1mf9sNI/p2whHaQ+3WoW/P3R5z5uaifqM1EbDAoGAN584\n" +
				"JnUnJcLwrnuBx1PkBmKxfFFbPeSHPzNNsSK3ERJdKOINbKbaX+7DlT4bRVbWvVj/\n" +
				"jcw/c2Ia0QTFpmOdnivjefIuehffOgvU8rsMeIBsgOvfiZGx0TP3+CCFDfRVqjIB\n" +
				"t3HAfAFyZfiP64nuzOERslL2XINafjZW5T0pZz8CgYAJ3UbEMbKdvIuK+uTl54R1\n" +
				"Vt6FO9T5bgtHR4luPKoBv1ttvSC6BlalgxA0Ts/AQ9tCsUK2JxisUcVgMjxBVvG0\n" +
				"lfq/EHpL0Wmn59SHvNwtHU2qx3Ne6M0nQtneCCfR78OcnqQ7+L+3YCMqYGJHNFSa\n" +
				"rd+dewfKoPnWw0WyGFEWCg==\n" +
				"-----END PRIVATE KEY-----");
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
		SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
		config.setKeyFormat(PEM);
		config.setPrivateKey(environment.getProperty("jasypt.encryptor.private-key-string"));
		config.setPublicKey("-----BEGIN PUBLIC KEY-----\n" +
				"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArQfyGCvBOdgmDGU6ciGP\n" +
				"VNB6jHsMip0b0qOrPvVTSJ/x0offjKARogA2tjGjyr3rUtwg9woMBqv/iyENR0GB\n" +
				"nIUa0jkYsznCKeygcflnNa4mrVf7XKXLhSwtY+kCe3diPk+0QPfEsfF9/aK6pWBU\n" +
				"FcrE8P2k2sF/8mo8dFJU1t6zQGPspHkNAgR6MLU8SjPZxnMS6EG722MdYhvSYAKs\n" +
				"nu02Hozqb4jh/gaQ/E6NkvM3DkqIyIYsRH2smstIFEb9CCiTdiz/OsJKQLgGy/pq\n" +
				"IVKtai3lnUxAayEV45Z61rNTOusNJf+icGhZxjqhAeoWjMxOCVmVC2GKa9sisqBg\n" +
				"kQIDAQAB\n" +
				"-----END PUBLIC KEY-----\n");
		StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);
		String message = "chupacabras";
		String encrypted = encryptor.encrypt(message);
		System.out.printf("Encrypted message %s\n", encrypted);
		String decrypted = encryptor.decrypt(encrypted);
		Assert.assertEquals(message, decrypted);
		System.out.println();
	}

}
