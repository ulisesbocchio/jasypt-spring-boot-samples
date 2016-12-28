# Sample App with Custom `jasypt.encryptor.property.prefix` and `jasypt.encryptor.property.suffix`

## Custom property prefix/suffix:

In `application.yml`:

```YAML
    jasypt:
      encryptor:
        property:
          prefix: "ENC@["
          suffix: "]"
```
This configuration basically looks for the provided `prefix` and `suffix`.
The properties in the `YAML` and `properties` resources that are encrypted thereby start with `"ENC@[` and end with `"]"`.

## Custom Environment

This app also showcases early initialization property encryption with a custom `Environment`:

```java
        new SpringApplicationBuilder()
                .environment(new EncryptableEnvironment(new StandardEnvironment(), new DefaultPropertyDetector("ENC@[", "]")))
                .sources(CustomDetectorDemoApplication.class).run(args);
```

**This is not required in most scenarios**