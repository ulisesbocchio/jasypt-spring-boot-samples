# Sample App using `@EncryptablePropertySource and @EncryptablePropertySources`

## Encryptable Property Sources:

In class `DBH2DemoApplication.java`:

```java
    @SpringBootApplication
    @EnableEncryptableProperties
    public class DBH2DemoApplication implements CommandLineRunner {
     ...
    }

```
It is showcased that a Datasource password (spring.datasource.password) property can be encrypted and works properly