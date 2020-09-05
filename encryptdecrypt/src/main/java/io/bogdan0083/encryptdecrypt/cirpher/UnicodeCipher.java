package io.bogdan0083.encryptdecrypt.cirpher;

public class UnicodeCipher implements Cipher {

    /**
     * Encode a string with unicode cipher algorithm by
     * moving every characters by some number down the unicode table
     * @param str string to encode
     * @param key number of positions to shift the character down the unicode table
     * @see <a href="https://unicode-table.com/en/">Unicode Characters table</a>
     * @return encoded string
     */
    @Override
    public String encode(String str, int key) {
        return str.chars()
                .map(ch -> (char) (ch + key))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * Decode a string encoded with unicode cipher algorithm
     * @param encodedString string to decode
     * @param key number of positions to shift the character up the unicode table
     * @see <a href="https://unicode-table.com/en/">Unicode Characters table</a>
     * @return decoded string
     */
    @Override
    public String decode(String encodedString, int key) {
        return encodedString.chars()
                .map(ch -> (char) (ch - key))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
