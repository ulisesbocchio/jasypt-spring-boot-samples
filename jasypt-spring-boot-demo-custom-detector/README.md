# Sample App with Custom `EncryptablePropertyDetector`

## Custom Property Detector:

In class `CustomDetectorDemoApplication.java`:

```java
    @Bean(name = "encryptablePropertyDetector")
    public EncryptablePropertyDetector encryptablePropertyDetector() {
        return new MyEncryptablePropertyDetector();
    }

    private static class MyEncryptablePropertyDetector implements EncryptablePropertyDetector {
        @Override
        public boolean isEncrypted(String value) {
            if (value != null) {
                return value.startsWith("ENC@");
            }
            return false;
        }

        @Override
        public String unwrapEncryptedValue(String value) {
            return value.substring("ENC@".length());
        }
    }

```
This detector basically looks for a `prefix` only. The String `"ENC@"`.
The properties in the `YAML` and `properties` resources that are encrypted thereby start with `"ENC@`.

## Custom Environment

This app also showcases early initialization property encryption with a custom `Environment`:

```java
        new SpringApplicationBuilder()
                .environment(new EncryptableEnvironment(new StandardEnvironment(), new MyEncryptablePropertyDetector()))
                .sources(CustomDetectorDemoApplication.class).run(args);
```

**This is not required in most scenarios**