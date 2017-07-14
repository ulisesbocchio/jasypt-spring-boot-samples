# Sample App with Custom `EncryptablePropertyResolver` Bean

## Custom Property Resolver:

In class `ResolverConfig.java`:

```java
    @Configuration
    public class ResolverConfig {
        @Bean(name="encryptablePropertyResolver")
        EncryptablePropertyResolver encryptablePropertyResolver(@Value("${jasypt.encryptor.password}") String password) {
            return new MyEncryptablePropertyResolver(password.toCharArray());
        }
    }

```
```java
    class MyEncryptablePropertyResolver implements EncryptablePropertyResolver {
    
    
        private final PooledPBEStringEncryptor encryptor;
    
        public MyEncryptablePropertyResolver(char[] password) {
            this.encryptor = new PooledPBEStringEncryptor();
            SimpleStringPBEConfig config = new SimpleStringPBEConfig();
            config.setPasswordCharArray(password);
            config.setAlgorithm("PBEWithMD5AndDES");
            config.setKeyObtentionIterations("1000");
            config.setPoolSize(1);
            config.setProviderName("SunJCE");
            config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
            config.setStringOutputType("base64");
            encryptor.setConfig(config);
        }
    
        @Override
        public String resolvePropertyValue(String value) {
            if (value != null && value.startsWith("{cipher}")) {
                return encryptor.decrypt(value.substring("{cipher}".length()));
            }
            return value;
        }
    }

```
This resolver basically looks for a `prefix` only, the String `"{cipher}"` and initializes it's own Encryptor internally.
The properties in the `YAML` and `properties` resources that are encrypted thereby start with `"{cipher}`.

Note here that even though a Jasypt `StringEncryptor` was used, you can provide any other library/encryption method of your choice.

## Custom Environment

This app also showcases early initialization property encryption with a custom `Environment`:

```java
        String password = System.getProperty("jasypt.encryptor.password");
        Assert.notNull(password, "Encryption password must be provided!");
        new SpringApplicationBuilder()
                .environment(new EncryptableEnvironment(new StandardEnvironment(), new MyEncryptablePropertyResolver(password.toCharArray())))
                .sources(CustomResolverDemoApplication.class).run(args);
```

**This is not required in most scenarios**