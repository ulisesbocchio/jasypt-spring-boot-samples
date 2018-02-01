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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;

@SpringBootApplication
@EnableWebSecurity
public class SampleSecureWebApplication {

    public static void main(String[] args) throws Exception {
        //System.setProperty("jasypt.encryptor.password", "password");
        SpringApplication.run(SampleSecureWebApplication.class, args);
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
}
