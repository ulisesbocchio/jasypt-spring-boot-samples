package demo;

import com.ulisesbocchio.jasyptspringboot.detector.DefaultPropertyDetector;
import com.ulisesbocchio.jasyptspringboot.environment.EncryptableEnvironment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CustomPrefixSuffixDemoApplication.class)
@BootstrapWith(CustomPrefixSuffixDemoApplicationTest.EncryptableEnvironmentBootstrapper.class)
public class CustomPrefixSuffixDemoApplicationTest {

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
						super.setEnvironment(new EncryptableEnvironment(environment, new DefaultPropertyDetector("ENC@[", "]")));
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
		Assert.assertEquals("chupacabras", environment.getProperty("secret.property"));
		Assert.assertEquals("chupacabras", environment.getProperty("secret2.property"));
	}

	@Test
	public void testServiceProperties() {
		Assert.assertEquals("chupacabras", service.getSecret());
		Assert.assertEquals("chupacabras", service.getSecret2());
	}

}
