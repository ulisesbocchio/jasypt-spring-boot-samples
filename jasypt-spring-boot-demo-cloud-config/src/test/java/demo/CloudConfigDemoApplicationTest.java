package demo;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CloudConfigDemoApplication.class)
public class CloudConfigDemoApplicationTest {

    @Autowired
    ConfigurableEnvironment environment;

    @Autowired
    StringEncryptor encryptor;

    static {
        System.setProperty("jasypt.encryptor.password", "password");
    }


    @Test
    public void testIndirectProperties() {
        Assert.assertEquals("https://uli:chupacabras@localhost:30000", environment.getProperty("endpoint"));
    }

    @Test
    public void decryptEncript() {
        System.out.println(encryptor.encrypt("config-server"));
        Assert.assertEquals(encryptor.decrypt("5HHB8n649TNGdJxLjV1PFO8xPmrhWeDFWizBn1y0kPQesBA5jb+Oz1yEP9YLBBtA"), "chupacabras");
    }
}
