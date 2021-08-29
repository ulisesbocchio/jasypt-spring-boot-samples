package demo;

import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricConfig;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricStringEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static com.ulisesbocchio.jasyptspringboot.util.AsymmetricCryptography.KeyFormat.DER;

@SpringBootTest(classes = SimpleAsymmetricDerDemoApplication.class)
public class SimpleAsymmetricDerDemoApplicationTest {

    @Autowired
    Environment environment;

    @Autowired
    MyService service;

    static {
        System.setProperty("jasypt.encryptor.privateKeyLocation", "classpath:private_key.der");
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
        SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
        config.setKeyFormat(DER);
        config.setPrivateKeyLocation(System.getProperty("jasypt.encryptor.privateKeyLocation"));
        config.setPublicKeyLocation("classpath:public_key.der");
        StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);
        String message = "chupacabras";
        String encrypted = encryptor.encrypt(message);
        System.out.printf("Encrypted message %s\n", encrypted);
        String decrypted = encryptor.decrypt(encrypted);
        Assertions.assertEquals(message, decrypted);
        System.out.println();
    }

}
