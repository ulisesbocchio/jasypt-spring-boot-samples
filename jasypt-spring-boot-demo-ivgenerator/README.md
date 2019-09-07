# Sample App using `jasypt-spring-boot-starter` dependency

 ## Asymmetric Encryption
 
 This app showcases the usage of asymmetric encryption/decryption using PBEWITHHMACSHA512ANDAES_256 from jasypt
 
## Encryptable Property Sources enabled by:

In `pom.xml`:

```xml
    <dependency>
        <groupId>com.github.ulisesbocchio</groupId>
        <artifactId>jasypt-spring-boot-starter</artifactId>
        <version>1.10</version>
    </dependency>

```
The starter jar has a `spring.factories` definition that Spring Boot uses to bootstrap Autoconfiguration. The autoconfiguration
 simply loads the `@EnableEncryptableProperties` annotation that decorates all property sources in the Spring `Environment` to be
 encryptable.
 
 
