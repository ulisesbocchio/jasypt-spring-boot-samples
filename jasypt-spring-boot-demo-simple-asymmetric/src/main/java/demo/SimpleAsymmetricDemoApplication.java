package demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

/**
 * Sample Boot application that showcases easy integration of Jasypt encryption by
 * simply adding {@literal @EnableEncryptableProperties} to any Configuration class.
 * For decryption a password is required and is set through system properties in this example,
 * but it could be passed command line argument too like this: --jasypt.encryptor.password=password
 *
 * @author Ulises Bocchio
 */
@SpringBootApplication
@Import(TestConfig.class)
//Uncomment this if not using jasypt-spring-boot-starter (use jasypt-spring-boot) dependency in pom instead
public class SimpleAsymmetricDemoApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleAsymmetricDemoApplication.class);

// Set the following system property or turn it into an environment variable for this app to run
//    static {
//        System.setProperty("jasypt.encryptor.privateKeyString", "-----BEGIN PRIVATE KEY-----\n" +
//                "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCtB/IYK8E52CYM\n" +
//                "ZTpyIY9U0HqMewyKnRvSo6s+9VNIn/HSh9+MoBGiADa2MaPKvetS3CD3CgwGq/+L\n" +
//                "IQ1HQYGchRrSORizOcIp7KBx+Wc1riatV/tcpcuFLC1j6QJ7d2I+T7RA98Sx8X39\n" +
//                "orqlYFQVysTw/aTawX/yajx0UlTW3rNAY+ykeQ0CBHowtTxKM9nGcxLoQbvbYx1i\n" +
//                "G9JgAqye7TYejOpviOH+BpD8To2S8zcOSojIhixEfayay0gURv0IKJN2LP86wkpA\n" +
//                "uAbL+mohUq1qLeWdTEBrIRXjlnrWs1M66w0l/6JwaFnGOqEB6haMzE4JWZULYYpr\n" +
//                "2yKyoGCRAgMBAAECggEAQxURhs1v3D0wgx27ywO3zeoFmPEbq6G9Z6yMd5wk7cMU\n" +
//                "vcpvoNVuAKCUlY4pMjDvSvCM1znN78g/CnGF9FoxJb106Iu6R8HcxOQ4T/ehS+54\n" +
//                "kDvL999PSBIYhuOPUs62B/Jer9FfMJ2veuXb9sGh19EFCWlMwILEV/dX+MDyo1qQ\n" +
//                "aNzbzyyyaXP8XDBRDsvPL6fPxL4r6YHywfcPdBfTc71/cEPksG8ts6um8uAVYbLI\n" +
//                "DYcsWopjVZY/nUwsz49xBCyRcyPnlEUJedyF8HANfVEO2zlSyRshn/F+rrjD6aKB\n" +
//                "V/yVWfTEyTSxZrBPl4I4Tv89EG5CwuuGaSagxfQpAQKBgQDXEe7FqXSaGk9xzuPa\n" +
//                "zXy8okCX5pT6545EmqTP7/JtkMSBHh/xw8GPp+JfrEJEAJJl/ISbdsOAbU+9KAXu\n" +
//                "PmkicFKbodBtBa46wprGBQ8XkR4JQoBFj1SJf7Gj9ozmDycozO2Oy8a1QXKhHUPk\n" +
//                "bPQ0+w3efwoYdfE67ZodpFNhswKBgQDN9eaYrEL7YyD7951WiK0joq0BVBLK3rwO\n" +
//                "5+4g9IEEQjhP8jSo1DP+zS495t5ruuuuPsIeodA79jI8Ty+lpYqqCGJTE6muqLMJ\n" +
//                "Diy7KlMpe0NZjXrdSh6edywSz3YMX1eAP5U31pLk0itMDTf2idGcZfrtxTLrpRff\n" +
//                "umowdJ5qqwKBgF+XZ+JRHDN2aEM0atAQr1WEZGNfqG4Qx4o0lfaaNs1+H+knw5kI\n" +
//                "ohrAyvwtK1LgUjGkWChlVCXb8CoqBODMupwFAqKL/IDImpUhc/t5uiiGZqxE85B3\n" +
//                "UWK/7+vppNyIdaZL13a1mf9sNI/p2whHaQ+3WoW/P3R5z5uaifqM1EbDAoGAN584\n" +
//                "JnUnJcLwrnuBx1PkBmKxfFFbPeSHPzNNsSK3ERJdKOINbKbaX+7DlT4bRVbWvVj/\n" +
//                "jcw/c2Ia0QTFpmOdnivjefIuehffOgvU8rsMeIBsgOvfiZGx0TP3+CCFDfRVqjIB\n" +
//                "t3HAfAFyZfiP64nuzOERslL2XINafjZW5T0pZz8CgYAJ3UbEMbKdvIuK+uTl54R1\n" +
//                "Vt6FO9T5bgtHR4luPKoBv1ttvSC6BlalgxA0Ts/AQ9tCsUK2JxisUcVgMjxBVvG0\n" +
//                "lfq/EHpL0Wmn59SHvNwtHU2qx3Ne6M0nQtneCCfR78OcnqQ7+L+3YCMqYGJHNFSa\n" +
//                "rd+dewfKoPnWw0WyGFEWCg==\n" +
//                "-----END PRIVATE KEY-----");
//    }

    @Autowired
    ApplicationContext appCtx;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(SimpleAsymmetricDemoApplication.class).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        MyService service = appCtx.getBean(MyService.class);
        Environment environment = appCtx.getBean(Environment.class);
        LOG.info("Asymmetric encryption");
        LOG.info("Environment's secret: {}", environment.getProperty("secret.property"));
        LOG.info("Environment's secret2: {}", environment.getProperty("secret2.property"));
        LOG.info("MyService's secret: {}", service.getSecret());
        LOG.info("MyService's secret2: {}", service.getSecret2());
        LOG.info("Done!");
    }
}
