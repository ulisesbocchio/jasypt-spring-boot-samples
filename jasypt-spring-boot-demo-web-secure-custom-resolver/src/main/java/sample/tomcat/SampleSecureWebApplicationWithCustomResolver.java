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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Predicate;

@SpringBootApplication
public class SampleSecureWebApplicationWithCustomResolver {

    @EnableWebSecurity
    @Configuration
    public class MyWebSecurityConfig {

    }

    @Bean(name="encryptablePropertyResolver")
    EncryptablePropertyResolver staticResolver() {
        return value -> Optional.ofNullable(value)
                .filter(Predicate.isEqual("ENC(nrmZtkF7T0kjG/VodDvBw93Ct8EgjCA+)"))
                .map(v -> "chupacabras")
                .orElse(value);
    }

    @Bean
    InMemoryUserDetailsManager userDetailsService(@Value("${security.user.name}") String name,
                                                  @Value("${security.user.password}") String password) {
        return new InMemoryUserDetailsManager(
//                Collections.singletonList(User.withUsername(name)
//                        .password(password)
//                        .roles("USER")
//                        .build())
                User.withDefaultPasswordEncoder()
                        .username(name)
                        .password(password)
                        .roles("USER")
                        .build()
        );
    }

    public static void main(String[] args) throws Exception {
        //System.setProperty("jasypt.encryptor.password", "password");
        SpringApplication.run(SampleSecureWebApplicationWithCustomResolver.class, args);
    }
}
