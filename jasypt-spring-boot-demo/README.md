# Main Demo App

This app showcases a bunch of different features.

## Use of `@EnableEncryptableProperties`

`DemoApplication.java` declares a `@EnableEncryptableProperties` annotation that bootstraps `jasypt-spring-boot` declaratively
and enables property encryption across the entire Spring Environment.

```java
@EnableEncryptableProperties
public class DemoApplication {

}
```

## Use of `@EncryptablePropertySource`

`DemoApplication.java` declares a few `@EncryptablePropertySource` annotations that load encryption-capable property sources.

```java
@EncryptablePropertySource(name = "EncryptedProperties2", value = "classpath:encrypted2.properties")
@EncryptablePropertySource(name = "EncryptedProperties3", value = "classpath:encrypted3.yml")
public class DemoApplication {

}
```

In this scenario is not strictly necessary since `@EnableEncryptableProperties` already decorates all property sources, so
when using `@EnableEncryptableProperties` (Or the starter jar) you can load encryption-capable properties with Spring's
`@PropertySource` annotation. 

## Use of `@PropertySource`

`DemoApplication.java` declares a `@PropertySource` annotations that loads an encryption-capable property source.

```java
@PropertySource(name = "EncryptedProperties", value = "classpath:encrypted.properties")
public class DemoApplication {

}
```

As mentioned above, the fact that we're using `@EnableEncryptableProperties`, it already decorates all property sources, so
when using `@EnableEncryptableProperties` (Or the starter jar) you can load encryption-capable properties with Spring's
 `@PropertySource` annotation. 

## Use of `@EnableConfigurationProperties`

`DemoApplication.java` declares a `@EnableConfigurationProperties` that allows `ItemConfig` to be populated from the environment.

`jasypt-spring-boot` works seamlessly with this annotation also so you can define your `@ConfigurationProperties` annotated classes
that contain references to encrypted properties.

```java
@EnableConfigurationProperties(ItemConfig.class)
public class DemoApplication {

}
```

## Use of `@ImportResource`

`DemoApplication.java` declares a `@ImportResource` that allows `testConfig.xml` to be loaded.

`jasypt-spring-boot` works seamlessly with this annotation also so you can import your `XML` that contains references to encrypted
properties.

```java
@ImportResource("classpath:/testConfig.xml")
public class DemoApplication {

}
```

## Use of `@Value`

`SimpleBean.java` and `MyService.java` both use the `@Value` annotation to inject decrypted versions of the encrypted
 properties present on the configuration files.