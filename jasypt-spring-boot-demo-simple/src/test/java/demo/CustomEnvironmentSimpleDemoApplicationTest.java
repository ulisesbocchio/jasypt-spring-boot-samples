package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomEnvironmentSimpleDemoApplication.class)
@ContextConfiguration(loader = CustomEnvironmentContextLoader.class)
public class CustomEnvironmentSimpleDemoApplicationTest {

    @Autowired
    ConfigurableEnvironment environment;

    @Autowired
    MyService service;

    @Autowired
    EncryptablePropertyResolver resolver;

    static {
        System.setProperty("jasypt.encryptor.password", "password");
        System.setProperty("ENCRYPTED_PASSWORD", "9ah+QnEdccHCkARkGZ7f0v5BLXXC+z0mr4hyjgE8T2G7mF75OBU1DgmC0YsGis8x");
    }

    @Test
    public void testEnvironmentProperties() {
        Assert.assertEquals("chupacabras", environment.getProperty("secret.property"));
        Assert.assertEquals("chupacabras", environment.getProperty("secret2.property"));
    }

    @Test
    public void testIndirectProperties() {
        Assert.assertEquals("chupacabras", environment.getProperty("indirect.secret.property"));
        Assert.assertEquals("chupacabras", environment.getProperty("indirect.secret.property2"));
        Assert.assertEquals("https://uli:chupacabras@localhost:30000", environment.getProperty("endpoint"));
    }

    @Test
    public void testServiceProperties() {
        Assert.assertEquals("chupacabras", service.getSecret());
        Assert.assertEquals("chupacabras", service.getSecret2());
    }

}
