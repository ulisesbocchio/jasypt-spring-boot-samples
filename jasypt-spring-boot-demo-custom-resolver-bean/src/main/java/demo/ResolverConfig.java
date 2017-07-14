package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author Ulises Bocchio
 */
@Configuration
public class ResolverConfig {
    @Bean(name="encryptablePropertyResolver")
    EncryptablePropertyResolver encryptablePropertyResolver(Environment environment) {
        return new MyEncryptablePropertyResolver(environment);
    }
}
