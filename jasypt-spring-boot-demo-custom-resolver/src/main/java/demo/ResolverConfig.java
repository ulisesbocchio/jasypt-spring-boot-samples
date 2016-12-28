package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ulises Bocchio
 */
@Configuration
public class ResolverConfig {
    @Bean(name="encryptablePropertyResolver")
    EncryptablePropertyResolver encryptablePropertyResolver(@Value("${jasypt.encryptor.password}") String password) {
        return new MyEncryptablePropertyResolver(password.toCharArray());
    }
}
