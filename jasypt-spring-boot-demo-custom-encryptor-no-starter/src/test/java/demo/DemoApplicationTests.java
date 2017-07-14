package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CustomEncryptorDemoApplicationNoStarter.class)
public class DemoApplicationTests {

	static {
		System.setProperty("jasypt.encryptor.password", "password");
	}

	@Test
	public void contextLoads() {
	}

}
