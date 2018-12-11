package demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

/**
 * Sample Boot application that showcases easy integration of Jasypt encryption by
 * simply adding {@literal @EnableEncryptableProperties} to any Configuration class.
 * For decryption a password is required and is set through system properties in this example,
 * but it could be passed command line argument too like this: --jasypt.encryptor.password=password
 *
 * @author Ulises Bocchio
 */
@SpringBootApplication
@Import(TestConfig.class)
//Uncomment this if not using jasypt-spring-boot-starter (use jasypt-spring-boot) dependency in pom instead
public class SimplePBEWITHHMACSHA512ANDAES_256MellowareDemoApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(SimplePBEWITHHMACSHA512ANDAES_256MellowareDemoApplication.class);

// Set the following system property or turn it into an environment variable for this app to run
//    static {
//        System.setProperty("jasypt.encryptor.password", "password");
//    }

    @Autowired
    ApplicationContext appCtx;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(SimplePBEWITHHMACSHA512ANDAES_256MellowareDemoApplication.class).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        MyService service = appCtx.getBean(MyService.class);
        Environment environment = appCtx.getBean(Environment.class);
        LOG.info("PBEWITHHMACSHA512ANDAES_256 melloware encryption");
        LOG.info("Environment's secret: {}", environment.getProperty("secret.property"));
        LOG.info("Environment's secret2: {}", environment.getProperty("secret2.property"));
        LOG.info("MyService's secret: {}", service.getSecret());
        LOG.info("MyService's secret2: {}", service.getSecret2());
        LOG.info("Done!");
    }
}
