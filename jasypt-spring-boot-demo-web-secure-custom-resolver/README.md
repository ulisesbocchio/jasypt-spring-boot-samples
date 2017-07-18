# Sample Web App using `jasypt-spring-boot` dependency

## Encryptable Property Sources enabled by:

In `SampleTomcatSslApplication`:

```xml
    @SpringBootApplication
    @EnableEncryptableProperties
    public class SampleTomcatSslApplication extends SpringBootServletInitializer {
        ...
    }

```
The `@EnableEncryptableProperties` annotation enables encryptable properties across the entire Spring Environment.
This app is a Web app with an Embedded tomcat container.