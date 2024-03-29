/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.tomcat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SampleWebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleWebApplicationTest {

	@Autowired
    TestRestTemplate testRestTemplate;

    @BeforeAll
    public static void beforeClass() {
        System.setProperty("jasypt.encryptor.password", "password");
    }

	@Test
	public void testHome() throws Exception {

		ResponseEntity<String> entity = testRestTemplate.getForEntity("/", String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		assertEquals("Hello, Secret Property: chupacabras", entity.getBody());
	}

}
