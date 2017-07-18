package demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

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
		Assert.assertEquals("chupacabras", environment.getProperty("secret.property"));
		Assert.assertEquals("chupacabras", environment.getProperty("secret2.property"));
		Assert.assertEquals("chupacabras", environment.getProperty("secret3.property"));
	}

	@Test
	public void testServiceProperties() {
		Assert.assertEquals("chupacabras", service.getSecret());
	}

    @Test
    public void testXMLProperties() {
        Assert.assertEquals("chupacabras", simpleBean.getValue());
    }

    @Test
    public void testConfigurationPropertiesProperties() {
        Assert.assertEquals("chupacabras", itemConfig.getPassword());
        Assert.assertEquals("my configuration", itemConfig.getConfigurationName());
        Assert.assertEquals(2, itemConfig.getItems().size());
        Assert.assertEquals("item1", itemConfig.getItems().get(0).getName());
        Assert.assertEquals(new Integer(1), itemConfig.getItems().get(0).getValue());
        Assert.assertEquals("item2", itemConfig.getItems().get(1).getName());
        Assert.assertEquals(new Integer(2), itemConfig.getItems().get(1).getValue());
    }
}
