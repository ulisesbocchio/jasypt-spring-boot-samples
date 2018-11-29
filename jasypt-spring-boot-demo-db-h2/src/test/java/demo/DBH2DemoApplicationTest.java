package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DBH2DemoApplication.class)
public class DBH2DemoApplicationTest {

    @Autowired
    ConfigurableEnvironment environment;

    @Autowired
    EncryptablePropertyResolver resolver;

    @Autowired
    PersonRepository repo;

    static {
        System.setProperty("jasypt.encryptor.password", "password");
    }

    @Test
    public void testDbPassword() {
        Assert.assertEquals("chupacabras", environment.getProperty("spring.datasource.password"));
    }

    @Test
    public void testDbAccess() {
        List<Person> people = repo.findByLastName("Bocchio");
        Assertions.assertThat(people).contains(new Person("Uli", "Bocchio"), new Person("Luca", "Bocchio"), new Person("Fiorella", "Bocchio"));
    }

}
