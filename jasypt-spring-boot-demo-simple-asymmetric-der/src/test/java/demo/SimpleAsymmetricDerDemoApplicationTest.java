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

import static com.ulisesbocchio.jasyptspringboot.util.AsymmetricCryptography.KeyFormat.DER;

@RunWith(SpringJUnit4ClassRunner.class)
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
        config.setKeyFormat(DER);
        config.setPrivateKeyLocation(System.getProperty("jasypt.encryptor.privateKeyLocation"));
        config.setPublicKeyLocation("classpath:public_key.der");
        StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);
        String message = "chupacabras";
        String encrypted = encryptor.encrypt(message);
        System.out.printf("Encrypted message %s\n", encrypted);
        String decrypted = encryptor.decrypt(encrypted);
        Assert.assertEquals(message, decrypted);
        System.out.println();
    }

}
