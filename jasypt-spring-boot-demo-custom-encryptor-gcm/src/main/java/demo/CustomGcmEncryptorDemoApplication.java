package demo;


import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleGCMConfig;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleGCMStringEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
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
@ComponentScan(excludeFilters = @ComponentScan.Filter( type = FilterType.REGEX, pattern = ".*"))
public class CustomGcmEncryptorDemoApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(CustomGcmEncryptorDemoApplication.class);

    @Autowired
    ApplicationContext appCtx;

    public static void main(String[] args) {
        //Enable proxy mode for intercepting encrypted properties
        //System.setProperty("jasypt.encryptor.proxyPropertySources", "true");
        new SpringApplicationBuilder().sources(CustomGcmEncryptorDemoApplication.class).run(args);
    }

    @Bean(name="jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        SimpleGCMConfig config = new SimpleGCMConfig();
        config.setSecretKeyLocation("classpath:secret_key.b64");
        return new SimpleGCMStringEncryptor(config);
    }

    @Override
    public void run(String... args) throws Exception {
        Environment environment = appCtx.getBean(Environment.class);
        LOG.info("Environment's secret: {}", environment.getProperty("secret.property"));
        LOG.info("Done!");
    }
}
