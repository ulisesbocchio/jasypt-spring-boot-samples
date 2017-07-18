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

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@SpringBootApplication
public class SampleSecureWebApplicationWithCustomResolver {

    @EnableWebSecurity
    @Configuration
    public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    }

    @Bean(name="encryptablePropertyResolver")
    EncryptablePropertyResolver staticResolver() {
        return value -> Optional.ofNullable(value)
                .filter(Predicate.isEqual("ENC(nrmZtkF7T0kjG/VodDvBw93Ct8EgjCA+)"))
                .map(v -> "chupacabras")
                .orElse(value);
    }

    public static void main(String[] args) throws Exception {
        //System.setProperty("jasypt.encryptor.password", "password");
        SpringApplication.run(SampleSecureWebApplicationWithCustomResolver.class, args);
    }
}
