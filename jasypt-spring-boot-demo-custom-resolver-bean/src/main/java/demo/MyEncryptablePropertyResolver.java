package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * @author Ulises Bocchio
 */
class MyEncryptablePropertyResolver implements EncryptablePropertyResolver {


    private final PooledPBEStringEncryptor encryptor;

    public MyEncryptablePropertyResolver(String password) {
//        char[] password = "password".toCharArray();
        this.encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPasswordCharArray(password.toCharArray());
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize(1);
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
    }

    @Override
    public String resolvePropertyValue(String value) {
        if (value != null && value.startsWith("{cipherr}")) {
            return encryptor.decrypt(value.substring("{cipherr}".length()));
        }
        return value;
    }
}
