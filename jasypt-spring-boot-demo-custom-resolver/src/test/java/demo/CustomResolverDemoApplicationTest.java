package demo;

import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextLoader;
import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = CustomResolverDemoApplication.class)
@BootstrapWith(CustomResolverDemoApplicationTest.EncryptableEnvironmentBootstrapper.class)
@SpringBootTest
public class CustomResolverDemoApplicationTest {

	@Configuration
	static class EncryptableEnvironmentBootstrapper extends SpringBootTestContextBootstrapper {
		static class EncryptableEnvironmentContextLoader extends SpringBootContextLoader {
		    @Override
            protected SpringApplication getSpringApplication() {
                return new SpringApplication() {

                    @Override
                    public void setEnvironment(ConfigurableEnvironment environment) {
                        String password = System.getProperty("jasypt.encryptor.password");
                        org.springframework.util.Assert.notNull(password, "Encryption password must be provided!");
                        super.setEnvironment(StandardEncryptableEnvironment.builder().resolver(new MyEncryptablePropertyResolver(password.toCharArray())).build());
                    }

                };
            }
		}

		@Override
		protected Class<? extends ContextLoader> getDefaultContextLoaderClass(
				Class<?> testClass) {
			return EncryptableEnvironmentContextLoader.class;
		}
	}

	@Autowired
	ConfigurableEnvironment environment;

	@Autowired
	MyService service;

	static {
		System.setProperty("jasypt.encryptor.password", "password");
	}

	@Test
	public void testEnvironmentProperties() {
		assertEquals("chupacabras", environment.getProperty("secret.property"));
		assertEquals("chupacabras", environment.getProperty("secret2.property"));
	}

	@Test
	public void testServiceProperties() {
		assertEquals("chupacabras", service.getSecret());
		assertEquals("chupacabras", service.getSecret2());
	}

}
