package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertySourceConverter;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
        Assertions.assertEquals("https://uli:chupacabras@localhost:30000", environment.getProperty("endpoint"));
    }

    @Test
    public void decryptEncript() {
        System.out.println(encryptor.encrypt("config-server"));
        Assertions.assertEquals(encryptor.decrypt("5HHB8n649TNGdJxLjV1PFO8xPmrhWeDFWizBn1y0kPQesBA5jb+Oz1yEP9YLBBtA"), "chupacabras");
    }

    @Test
    public void scopeRefresh() {
        Map<String, Object> map = new HashMap<>();
        map.put("testPropertyThatWillChange", "ENC(rSbEWcizD0aYexvDcky4yotGdtgRp125B88JDHwTsTIm1VRKsYzcFlEsL9jyqCvNn5kxwfGgOVwsnZDW+w7IFg==)");
        MapPropertySource ps = new MapPropertySource("refreshtest", map);
        EncryptablePropertySourceConverter propertyConverter = applicationContext.getBean(EncryptablePropertySourceConverter.class);
        PropertySource<?> eps = propertyConverter.makeEncryptable(ps);
        environment.getPropertySources().addLast(eps);
        Assertions.assertEquals("theValueYouWantToEncrypt", environment.getProperty("testPropertyThatWillChange"));
        map.put("testPropertyThatWillChange", "ENC(AZWn9RKhE+y3KdgB5NbCLjzHHxPe+PhAPXTd/r4Nm9BQ0NtcEoDS896yNsRPaRZxBPQtww+Vu31G7Mdtr/6UFQ==)");
        // change at the value level now reflects immediately due to new caching strategy
        // Assertions.assertEquals("theValueYouWantToEncrypt", environment.getProperty("testPropertyThatWillChange"));
        // Assertions.assertEquals("theNewValueYouWantToEncrypt", environment.getProperty("testPropertyThatWillChange"));
        applicationContext.publishEvent(new RefreshScopeRefreshedEvent());
        Assertions.assertEquals("theNewValueYouWantToEncrypt", environment.getProperty("testPropertyThatWillChange"));
        environment.getPropertySources().remove("refreshtest");
        Assertions.assertNull(environment.getProperty("testPropertyThatWillChange"));
    }

    @Test
    public void environmentChanged() {
        Map<String, Object> map = new HashMap<>();
        map.put("testPropertyThatWillChange", "ENC(rSbEWcizD0aYexvDcky4yotGdtgRp125B88JDHwTsTIm1VRKsYzcFlEsL9jyqCvNn5kxwfGgOVwsnZDW+w7IFg==)");
        MapPropertySource ps = new MapPropertySource("refreshtest", map);
        EncryptablePropertySourceConverter propertyConverter = applicationContext.getBean(EncryptablePropertySourceConverter.class);
        PropertySource<?> eps = propertyConverter.makeEncryptable(ps);
        environment.getPropertySources().addLast(eps);
        Assertions.assertEquals("theValueYouWantToEncrypt", environment.getProperty("testPropertyThatWillChange"));
        environment.getPropertySources().replace("refreshtest", ps);
        map.put("testPropertyThatWillChange", "ENC(AZWn9RKhE+y3KdgB5NbCLjzHHxPe+PhAPXTd/r4Nm9BQ0NtcEoDS896yNsRPaRZxBPQtww+Vu31G7Mdtr/6UFQ==)");
        Assertions.assertEquals("ENC(AZWn9RKhE+y3KdgB5NbCLjzHHxPe+PhAPXTd/r4Nm9BQ0NtcEoDS896yNsRPaRZxBPQtww+Vu31G7Mdtr/6UFQ==)", environment.getProperty("testPropertyThatWillChange"));
        applicationContext.publishEvent(new EnvironmentChangeEvent(Collections.emptySet()));
        Assertions.assertEquals("theNewValueYouWantToEncrypt", environment.getProperty("testPropertyThatWillChange"));
        environment.getPropertySources().remove("refreshtest");
        Assertions.assertNull(environment.getProperty("testPropertyThatWillChange"));
    }

    @Test
    public void environmentChangedWithScope() {
        Map<String, Object> map = new HashMap<>();
        map.put("testPropertyThatWillChange", "ENC(rSbEWcizD0aYexvDcky4yotGdtgRp125B88JDHwTsTIm1VRKsYzcFlEsL9jyqCvNn5kxwfGgOVwsnZDW+w7IFg==)");
        MapPropertySource ps = new MapPropertySource("refreshtest", map);
        EncryptablePropertySourceConverter propertyConverter = applicationContext.getBean(EncryptablePropertySourceConverter.class);
        PropertySource<?> eps = propertyConverter.makeEncryptable(ps);
        environment.getPropertySources().addLast(eps);
        Assertions.assertEquals("theValueYouWantToEncrypt", environment.getProperty("testPropertyThatWillChange"));
        environment.getPropertySources().replace("refreshtest", ps);
        map.put("testPropertyThatWillChange", "ENC(AZWn9RKhE+y3KdgB5NbCLjzHHxPe+PhAPXTd/r4Nm9BQ0NtcEoDS896yNsRPaRZxBPQtww+Vu31G7Mdtr/6UFQ==)");
        Assertions.assertEquals("ENC(AZWn9RKhE+y3KdgB5NbCLjzHHxPe+PhAPXTd/r4Nm9BQ0NtcEoDS896yNsRPaRZxBPQtww+Vu31G7Mdtr/6UFQ==)", environment.getProperty("testPropertyThatWillChange"));
        applicationContext.publishEvent(new RefreshScopeRefreshedEvent());
        Assertions.assertEquals("theNewValueYouWantToEncrypt", environment.getProperty("testPropertyThatWillChange"));
        environment.getPropertySources().remove("refreshtest");
        Assertions.assertNull(environment.getProperty("testPropertyThatWillChange"));
    }
}
