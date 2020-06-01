package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyFilter;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertySourceConverter;
import com.ulisesbocchio.jasyptspringboot.InterceptionMode;
import com.ulisesbocchio.jasyptspringboot.configuration.EncryptablePropertyResolverConfiguration;
import com.ulisesbocchio.jasyptspringboot.wrapper.EncryptableMapPropertySourceWrapper;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.ulisesbocchio.jasyptspringboot.configuration.EncryptablePropertyResolverConfiguration.FILTER_BEAN_NAME;
import static com.ulisesbocchio.jasyptspringboot.configuration.EncryptablePropertyResolverConfiguration.RESOLVER_BEAN_NAME;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CloudConfigDemoApplication.class)
public class CloudConfigDemoApplicationTest {

    @Autowired
    ConfigurableEnvironment environment;

    @Autowired
    StringEncryptor encryptor;

    @Autowired
    ApplicationContext applicationContext;

    static {
        System.setProperty("jasypt.encryptor.password", "password");
    }


    @Test
    public void testIndirectProperties() {
        Assert.assertEquals("https://uli:chupacabras@localhost:30000", environment.getProperty("endpoint"));
    }

    @Test
    public void decryptEncript() {
        System.out.println(encryptor.encrypt("config-server"));
        Assert.assertEquals(encryptor.decrypt("5HHB8n649TNGdJxLjV1PFO8xPmrhWeDFWizBn1y0kPQesBA5jb+Oz1yEP9YLBBtA"), "chupacabras");
    }

    @Test
    public void scopeRefresh() {
        Map<String, Object> map = new HashMap<>();
        map.put("testPropertyThatWillChange", "ENC(rSbEWcizD0aYexvDcky4yotGdtgRp125B88JDHwTsTIm1VRKsYzcFlEsL9jyqCvNn5kxwfGgOVwsnZDW+w7IFg==)");
        MapPropertySource ps = new MapPropertySource("refreshtest", map);
        EncryptablePropertySourceConverter propertyConverter = applicationContext.getBean(EncryptablePropertySourceConverter.class);
        PropertySource<?> eps = propertyConverter.makeEncryptable(ps);
        environment.getPropertySources().addLast(eps);
        Assert.assertEquals("theValueYouWantToEncrypt", environment.getProperty("testPropertyThatWillChange"));
        map.put("testPropertyThatWillChange", "ENC(AZWn9RKhE+y3KdgB5NbCLjzHHxPe+PhAPXTd/r4Nm9BQ0NtcEoDS896yNsRPaRZxBPQtww+Vu31G7Mdtr/6UFQ==)");
        Assert.assertEquals("theValueYouWantToEncrypt", environment.getProperty("testPropertyThatWillChange"));
        applicationContext.publishEvent(new RefreshScopeRefreshedEvent());
        Assert.assertEquals("theNewValueYouWantToEncrypt", environment.getProperty("testPropertyThatWillChange"));
        environment.getPropertySources().remove("refreshtest");
        Assert.assertNull(environment.getProperty("testPropertyThatWillChange"));
    }

    @Test
    public void environmentChanged() {
        Map<String, Object> map = new HashMap<>();
        map.put("testPropertyThatWillChange", "ENC(rSbEWcizD0aYexvDcky4yotGdtgRp125B88JDHwTsTIm1VRKsYzcFlEsL9jyqCvNn5kxwfGgOVwsnZDW+w7IFg==)");
        MapPropertySource ps = new MapPropertySource("refreshtest", map);
        EncryptablePropertySourceConverter propertyConverter = applicationContext.getBean(EncryptablePropertySourceConverter.class);
        PropertySource<?> eps = propertyConverter.makeEncryptable(ps);
        environment.getPropertySources().addLast(eps);
        Assert.assertEquals("theValueYouWantToEncrypt", environment.getProperty("testPropertyThatWillChange"));
        environment.getPropertySources().replace("refreshtest", ps);
        map.put("testPropertyThatWillChange", "ENC(AZWn9RKhE+y3KdgB5NbCLjzHHxPe+PhAPXTd/r4Nm9BQ0NtcEoDS896yNsRPaRZxBPQtww+Vu31G7Mdtr/6UFQ==)");
        Assert.assertEquals("ENC(AZWn9RKhE+y3KdgB5NbCLjzHHxPe+PhAPXTd/r4Nm9BQ0NtcEoDS896yNsRPaRZxBPQtww+Vu31G7Mdtr/6UFQ==)", environment.getProperty("testPropertyThatWillChange"));
        applicationContext.publishEvent(new EnvironmentChangeEvent(Collections.emptySet()));
        Assert.assertEquals("theNewValueYouWantToEncrypt", environment.getProperty("testPropertyThatWillChange"));
        environment.getPropertySources().remove("refreshtest");
        Assert.assertNull(environment.getProperty("testPropertyThatWillChange"));
    }
}
