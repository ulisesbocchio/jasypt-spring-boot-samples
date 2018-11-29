package demo;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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
public class DBH2DemoApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DBH2DemoApplication.class);

    static {
        System.setProperty("jasypt.encryptor.password", "password");
    }

    @Autowired
    ApplicationContext appCtx;

    public static void main(String[] args) {
        //try commenting the following line out and run the app from the command line passing the password as
        //a command line argument: java -jar target/jasypt-spring-boot-demo-0.0.1-SNAPSHOT.jar --jasypt.encryptor.password=password
        //System.setProperty("jasypt.encryptor.password", "password");
        //Enable proxy mode for intercepting encrypted properties
        //System.setProperty("jasypt.encryptor.proxyPropertySources", "true");
        new SpringApplicationBuilder()
                //.environment(new StandardEncryptableEnvironment())
                .sources(DBH2DemoApplication.class)
                .run(args);
    }

    @Bean
    public static BeanDefinitionRegistryPostProcessor dbFilenamePropertySourcePostProcessor(ConfigurableEnvironment env) {
        return new BeanDefinitionRegistryPostProcessor() {
            @SneakyThrows
            @Override
            public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
                Map<String, Object> map = new HashMap<>();
                map.put("dbfile", new ClassPathResource("./").getURL() + "/testdb");
                PropertySource ps = new MapPropertySource("dbfilename", map);
                env.getPropertySources().addLast(ps);
            }

            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

            }
        };
    }

    private void populateDb() {
        PersonRepository repo = appCtx.getBean(PersonRepository.class);
        repo.save(new Person("Uli", "Bocchio"));
        repo.save(new Person("Luca", "Bocchio"));
        repo.save(new Person("Fiorella", "Bocchio"));

    }

    @SneakyThrows
    private void createUserWithPassword() {
        DataSource ds = appCtx.getBean(DataSource.class);
        Connection con = ds.getConnection();
        Statement stmt = con.createStatement();
        stmt.execute("CREATE USER IF NOT EXISTS ULI PASSWORD 'chupacabras'");
        stmt.execute("GRANT ALL ON person to ULI");
    }
    @Override
    @SneakyThrows
    public void run(String... args) {
        //populateDb();
        //createUserWithPassword();
        LOG.info("**********************************************************");
        LOG.info("**********************************************************");
        LOG.info("DB File: {}", appCtx.getEnvironment().getProperty("dbfile"));
        LOG.info("DB User: {}", appCtx.getEnvironment().getProperty("spring.datasource.username"));
        LOG.info("DB pass: {}", appCtx.getEnvironment().getProperty("spring.datasource.password"));
        LOG.info("DATA:");
        PersonRepository repo = appCtx.getBean(PersonRepository.class);
        LOG.info("{}", repo.findByLastName("Bocchio"));
        LOG.info("**********************************************************");
        LOG.info("**********************************************************");
        LOG.info("Done!");
    }
}
