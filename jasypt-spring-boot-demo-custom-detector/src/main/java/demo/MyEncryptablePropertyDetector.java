package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;

class MyEncryptablePropertyDetector implements EncryptablePropertyDetector {
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
