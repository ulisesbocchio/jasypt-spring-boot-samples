package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NoPasswordDemoApplication.class)
public class NoPasswordDemoApplicationTest {

    @Autowired
    ConfigurableEnvironment environment;

    @Autowired
    EncryptablePropertyResolver resolver;

//    static {
//        System.setProperty("jasypt.encryptor.password", "password");
//    }

    @Test
    public void testUsername() {
        Assert.assertEquals("uli", environment.getProperty("username"));
    }
}
