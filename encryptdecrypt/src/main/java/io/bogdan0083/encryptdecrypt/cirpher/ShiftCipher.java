package io.bogdan0083.encryptdecrypt.cirpher;

public class ShiftCipher implements Cipher {

    /**
     * Encode a latin string with shift algorithm, a.k.a "Caesar Cipher"
     * Non-alphabetic characters are skipped.
     *
     * @param str string to encode
     * @param key number of positions to shift the character down the alphabet
     * @return encoded string
     * @see <a href="https://en.wikipedia.org/wiki/Caesar_cipher">Ceasar Cipher</a>
     */
    @Override
    public String encode(String str, int key) {
        return str.chars()
                .map(ch -> {
                    if (!Character.isAlphabetic(ch)) {
                        return ch;
                    }
                    if (Character.isLowerCase(ch)) {
                        int newChar = ch + key;
                        return Math.min(newChar, 'a' + (newChar % 'z' - 1));
                    } else {
                        int newChar = ch + key;
                        return Math.min(newChar, 'A' + (newChar % 'Z' - 1));
                    }
                })
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * Decode an encoded latin string with shift algorithm, a.k.a "Caesar Cipher".
     * To successfully decode the string you need to know what key was used for encoding.
     * Non-alphabetic characters are skipped.
     *
     * @param  encodedString string to decode
     * @param  key           number of positions to shift the character up the alphabet
     * @return decoded string
     * @see <a href="https://en.wikipedia.org/wiki/Caesar_cipher">Ceasar Cipher</a>
     */
    @Override
    public String decode(String encodedString, int key) {
        return encodedString.chars()
                .map(ch -> {
                    if (!Character.isAlphabetic(ch)) {
                        return ch;
                    }
                    if (Character.isLowerCase(ch)) {
                        int newChar = ch - key;
                        return Math.max(newChar, 'z' + 1 - ('a' % newChar));
                    } else {
                        int newChar = ch - key;
                        return Math.max(newChar, 'Z' + 1 - ('A' % newChar));
                    }
                })
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
