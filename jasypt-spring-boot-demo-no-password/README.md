# Sample App using `@EncryptablePropertySource and @EncryptablePropertySources`

## Encryptable Property Sources:

In class `NoPasswordDemoApplication.java`:

```java
    @SpringBootApplication
    @EnableEncryptableProperties
    public class NoPasswordDemoApplication implements CommandLineRunner {
     ...
    }

```
The jasypt encryptor password is not set and it does not break the application nor the tests because there is no ENC()
decorated property in any of the config files (application.yml)