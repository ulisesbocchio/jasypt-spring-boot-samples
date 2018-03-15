# Sample App using spring-cloud-config


Notice the manual hookup of:

```properties
org.springframework.cloud.bootstrap.BootstrapConfiguration=com.ulisesbocchio.jasyptspringboot.configuration.EnableEncryptablePropertiesConfiguration
```

in [META-INF/spring.factories](src/main/resources/META-INF/spring.factories)

This is only necessary if using `@EnableEncryptableProperties` and it is not necessary when using `jasypt-spring-boot-starter`

## Build and Run (needs Docker)

```bash
./run.sh -b
```
