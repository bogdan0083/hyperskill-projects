package io.bogdan0083.encryptdecrypt.cirpher;

import io.bogdan0083.encryptdecrypt.exception.UnknownCipherAlgorithm;

public interface Cipher {
    static Cipher getInstance(String cipherName) {

        if (cipherName == null) {
            return new ShiftCipher();
        }

        switch (cipherName) {
            case "unicode":
                return new UnicodeCipher();
            case "shift":
            default:
                return new ShiftCipher();
        }
    }

    String encode(String str, int key);

    String decode(String str, int key);

    default String runAction(CipherMode action, String str, int key) throws UnknownCipherAlgorithm {
        switch (action) {
            case ENCODE:
                return encode(str, key);
            case DECODE:
                return decode(str, key);
            case UNKNOWN:
            default:
                throw new UnknownCipherAlgorithm("Unknown action");
        }
    }
}
