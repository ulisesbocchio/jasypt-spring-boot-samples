package demo;

import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.core.env.ConfigurableEnvironment;

public class CustomEnvironmentContextLoader extends SpringBootContextLoader {
    @Override
    protected ConfigurableEnvironment getEnvironment() {
        return new StandardEncryptableEnvironment();
    }
}
