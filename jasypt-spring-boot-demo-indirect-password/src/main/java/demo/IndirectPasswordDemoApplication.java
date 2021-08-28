package demo;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource(name = "application2", value = "classpath:application2.properties")
@EncryptablePropertySources({@EncryptablePropertySource("classpath:encrypted.properties"),
        @EncryptablePropertySource(name = "IgnoredResource_FileDoesNotExist", value = "classpath:does_not_exists.properties", ignoreResourceNotFound = true)})
@Import(TestConfig.class)
@EnableEncryptableProperties
public class IndirectPasswordDemoApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(IndirectPasswordDemoApplication.class);

    @Autowired
    ApplicationContext appCtx;

    public static void main(String[] args) {
        //try commenting the following line out and run the app from the command line passing the password as
        //a command line argument: java -jar target/jasypt-spring-boot-demo-0.0.1-SNAPSHOT.jar --jasypt.encryptor.password=password
        System.setProperty("custom.encryptor.password", "password");
//        System.setProperty("jasypt.encryptor.password", "password");
        System.setProperty("ENCRYPTED_PASSWORD", "9ah+QnEdccHCkARkGZ7f0v5BLXXC+z0mr4hyjgE8T2G7mF75OBU1DgmC0YsGis8x");
        //Enable proxy mode for intercepting encrypted properties
        //System.setProperty("jasypt.encryptor.proxyPropertySources", "true");
        new SpringApplicationBuilder()
                //.environment(new StandardEncryptableEnvironment())
                .sources(IndirectPasswordDemoApplication.class)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        MyService service = appCtx.getBean(MyService.class);
        Environment environment = appCtx.getBean(Environment.class);
        LOG.info("MyService's secret: {}", service.getSecret());
        LOG.info("MyService's secret2: {}", service.getSecret2());
        LOG.info("Environment's secret: {}", environment.getProperty("secret.property"));
        LOG.info("Environment's secret2: {}", environment.getProperty("secret2.property"));
        //The EncryptableEnvironment should be able to resolve placeholders with encrypted values, if you don't need
        // to resolve properties like ${NAME:ENC(encrypted_value)} then you don't need to specify EncryptableEnvironment.
        LOG.info("Environment's Indirect secret property: {}", environment.getProperty("indirect.secret.property"));
        LOG.info("Environment's Indirect secret property2: {}", environment.getProperty("indirect.secret.property2"));
        LOG.info("Environment's Indirect secret property 3: {}", environment.getProperty("endpoint"));
        LOG.info("Environment's Indirect secret property 4: {}", environment.getProperty("endpoint2"));
        LOG.info("application yml defaultPassword2: {}", environment.getProperty("defaultPassword2"));
        LOG.info("Done!");
    }
}
