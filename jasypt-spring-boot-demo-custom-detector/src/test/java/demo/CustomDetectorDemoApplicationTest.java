package demo;

import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import org.junit.jupiter.api.Assertions;
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

@SpringBootTest(classes = CustomDetectorDemoApplication.class)
@BootstrapWith(CustomDetectorDemoApplicationTest.EncryptableEnvironmentBootstrapper.class)
public class CustomDetectorDemoApplicationTest {

	@Configuration
	static class EncryptableEnvironmentBootstrapper extends SpringBootTestContextBootstrapper {
		static class EncryptableEnvironmentContextLoader extends SpringBootContextLoader {
			@Override
			protected SpringApplication getSpringApplication() {
				return new SpringApplication() {

					@Override
					public void setEnvironment(ConfigurableEnvironment environment) {
						String password = environment.getRequiredProperty("jasypt.encryptor.password");
						super.setEnvironment(StandardEncryptableEnvironment.builder().detector(new MyEncryptablePropertyDetector()).build());
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
		Assertions.assertEquals("chupacabras", environment.getProperty("secret.property"));
		Assertions.assertEquals("chupacabras", environment.getProperty("secret2.property"));
	}

	@Test
	public void testServiceProperties() {
		Assertions.assertEquals("chupacabras", service.getSecret());
		Assertions.assertEquals("chupacabras", service.getSecret2());
	}

}
