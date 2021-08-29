package demo;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

/**
 * Sample Boot application that showcases easy integration of Jasypt encryption by
 * simply adding {@literal @EnableEncryptableProperties} to any Configuration class.
 * For decryption a password is required and is set through system properties in this example,
 * but it could be passed command line argument too like this: --jasypt.encryptor.password=password
 *
 * @author Ulises Bocchio
 */
@SpringBootApplication
@EnableEncryptableProperties
public class NoPasswordDemoApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(NoPasswordDemoApplication.class);

    // static {
    //     System.setProperty("jasypt.encryptor.password", "password");
    // }

    @Autowired
    ApplicationContext appCtx;

    public static void main(String[] args) {
        //try commenting the following line out and run the app from the command line passing the password as
        //a command line argument: java -jar target/jasypt-spring-boot-demo-0.0.1-SNAPSHOT.jar --jasypt.encryptor.password=password
        //System.setProperty("jasypt.encryptor.password", "password");
        //Enable proxy mode for intercepting encrypted properties
        //System.setProperty("jasypt.encryptor.proxyPropertySources", "true");
        new SpringApplicationBuilder()
                .environment(new StandardEncryptableEnvironment())
                .sources(NoPasswordDemoApplication.class)
                .run(args);
    }

    @Override
    public void run(String... args) {
        LOG.info("**********************************************************");
        LOG.info("**********************************************************");
        LOG.info("User: {}", appCtx.getEnvironment().getProperty("username"));
        LOG.info("Test User: {}", appCtx.getEnvironment().getProperty("test.user"));
        LOG.info("**********************************************************");
        LOG.info("**********************************************************");
        LOG.info("Done!");
    }
}
