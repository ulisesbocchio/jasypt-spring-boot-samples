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

import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableServletEnvironment;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EncryptableEnvironmentContextLoader extends SpringBootContextLoader {

    @Override
    protected ConfigurableEnvironment getEnvironment() {
        return StandardEncryptableServletEnvironment.builder().build();
    }
}

@ContextConfiguration(loader = EncryptableEnvironmentContextLoader.class)
@SpringBootTest(classes = SampleTomcatSslApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleTomcatSslApplicationTest {

    @LocalServerPort
    int port;

    static {
        System.setProperty("jasypt.encryptor.password", "password");
    }

    @Test
    public void testHome() throws Exception {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        customize(testRestTemplate.getRestTemplate());
        ResponseEntity<String> entity = testRestTemplate.getForEntity("https://localhost:" + port, String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals("Hello, Secret Property: chupacabras", entity.getBody());
    }

    @SneakyThrows
    public void customize(RestTemplate restTemplate) {
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build());

        PoolingHttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create().setSSLSocketFactory(socketFactory).build();

        HttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }
}
