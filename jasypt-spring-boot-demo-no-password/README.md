# Sample App using `@EncryptablePropertySource and @EncryptablePropertySources`

## Encryptable Property Sources:

In class `SimpleDemoApplication.java`:

```java
    @SpringBootApplication
    @EncryptablePropertySources({@EncryptablePropertySource("classpath:encrypted.properties"),
                                 @EncryptablePropertySource(name = "IgnoredResource_FileDoesNotExist", value = "classpath:does_not_exists.properties", ignoreResourceNotFound = true)})
    @Import(TestConfig.class)
    public class SimpleDemoApplication implements CommandLineRunner {
     ...
    }

```
The `@EncryptablePropertySources` annotation is used to group `@EncryptablePropertySource` annotations. Each of the later
defines a property source that can contain encrypted properties. 

This method is the most explicit one, meaning that you can't place encrypted properties in `application.properties` or
in any other property source that is not declared with this annotation.

Note: Even though `@EncryptablePropertySource` is meant for encrypted properties, it also supports YAML files, so you
could use this annotation to load other YAML config files regardless of any encryption.