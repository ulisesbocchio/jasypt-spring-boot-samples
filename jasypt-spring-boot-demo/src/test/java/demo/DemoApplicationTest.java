package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTest {

	@Autowired
	ConfigurableEnvironment environment;

	@Autowired
	MyService service;

	@Autowired
	ItemConfig itemConfig;

	@Autowired
    SimpleBean simpleBean;

	static {
		System.setProperty("jasypt.encryptor.password", "password");
	}

	@Test
	public void testEnvironmentProperties() {
		assertEquals("chupacabras", environment.getProperty("secret.property"));
		assertEquals("chupacabras", environment.getProperty("secret2.property"));
		assertEquals("chupacabras", environment.getProperty("secret3.property"));
	}

	@Test
	public void testServiceProperties() {
		assertEquals("chupacabras", service.getSecret());
	}

    @Test
    public void testXMLProperties() {
        assertEquals("chupacabras", simpleBean.getValue());
    }

    @Test
    public void testConfigurationPropertiesProperties() {
        assertEquals("chupacabras", itemConfig.getPassword());
        assertEquals("my configuration", itemConfig.getConfigurationName());
        assertEquals(2, itemConfig.getItems().size());
        assertEquals("item1", itemConfig.getItems().get(0).getName());
        assertEquals(new Integer(1), itemConfig.getItems().get(0).getValue());
        assertEquals("item2", itemConfig.getItems().get(1).getName());
        assertEquals(new Integer(2), itemConfig.getItems().get(1).getValue());
    }
}
